package com.haishi.admin.system.service;

import com.haishi.admin.auth.service.AuthService;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import com.haishi.admin.system.dto.*;
import com.haishi.admin.system.entity.Permission;
import com.haishi.admin.system.entity.QUser;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.UserRepository;
import com.haishi.admin.system.entity.Role;
import com.haishi.admin.system.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final RedissonClient redissonClient;
    @Resource
    private RoleService roleService;

    @Resource
    private AuthService authService;


    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    private JPAQuery<User> buildQuery(UserQueryDTO queryDTO) {
        JPAQuery<User> query = jpaQueryFactory.selectFrom(QUser.user);
        query.where(QUser.user.status.eq(1));
        query.orderBy(QUser.user.id.desc());
        return query;
    }

    public List<User> list(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        return query.fetch();
    }

    @Cacheable(value = "users", key = "'listUserIdAndNickName'")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<UserIdAndNickNameDTO> listUserIdAndNickName() {
        return list(new UserQueryDTO()).stream().map(userMapper::toUserIdAndNickNameDTO).collect(Collectors.toList());
    }

    public PageDTO<UserDto> page(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<User> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data.stream().map(userMapper::toDto).collect(Collectors.toList()));
        return queryDTO;
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserDto save(UserDto userDto) {
        boolean existsByUsername = userRepository.existsByUsername(userDto.username());
        User user = new User();
        if (userDto.id() != null) {
            user = userRepository.findById(userDto.id()).orElseThrow(() -> new BizException(BizExceptionEnum.USER_NOT_FOUND));
            if (existsByUsername && !user.getUsername().equals(userDto.username())) {
                throw new BizException(BizExceptionEnum.USERNAME_EXIST);
            }
        } else {
            if (existsByUsername) {
                throw new BizException(BizExceptionEnum.USERNAME_EXIST);
            }
        }

        userMapper.partialUpdate(userDto, user);
        user.setRoles(roleService.getByIds(userDto.roleIds()));
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) { // 不是加密密码
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.save(user);
        // 删除session
        authService.clearSession(user.getId());
        return userMapper.toDto(user);
    }

    public boolean delete(Long id) {
        authService.logout(id);
        userRepository.deleteById(id);
        return true;
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Cacheable(value = "session", key = "#userId")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Userinfo getUserinfoByUserId(Long userId) {
        log.info("getUserinfoByUserId: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException(BizExceptionEnum.USER_NOT_FOUND));
        Userinfo userinfo = new Userinfo();
        userinfo.setId(user.getId());
        userinfo.setUsername(user.getUsername());
        userinfo.setNickname(user.getNickname());
        userinfo.setDeptId(user.getDeptId());
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();

        for (Role role : user.getRoles()) {
            roles.add(role.getCode());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getCode());
            }
        }
        userinfo.setRoles(roles);
        permissions.add("DATA__" + user.getDataPermission());
        userinfo.setPermissions(permissions.stream().distinct().toList());
        return userinfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "session", key = "#userId")
    public User saveRole(Long userId, List<Long> roleIds) {
        User user = getById(userId);
        roleService.getByIds(roleIds).forEach(role -> user.getRoles().add(role));
        userRepository.save(user);
        return user;
    }

}