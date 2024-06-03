package com.haishi.admin.system.service;

import com.haishi.admin.system.dto.UserQueryDTO;
import com.haishi.admin.system.dto.Userinfo;
import com.haishi.admin.system.entity.QUser;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.UserRepository;
import com.haishi.admin.system.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;
    @Resource
    private RoleService roleService;

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    private JPAQuery<User> buildQuery(UserQueryDTO queryDTO) {
        JPAQuery<User> query = jpaQueryFactory.selectFrom(QUser.user);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<User> list(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<User> page(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<User> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User getUserByUserName(String username) {
        QUser q = QUser.user;
        return jpaQueryFactory.selectFrom(q).where(q.username.eq(username)).fetchOne();
    }

    @Cacheable(value = "session", key = "#userId")
    public Userinfo getUserinfoByUserId(Long userId) {
        User user = getById(userId);
        Userinfo userinfo = new Userinfo();
        userinfo.setId(user.getId());
        userinfo.setUsername(user.getUsername());
        userinfo.setNickname(user.getNickname());
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getCode()));
        userinfo.setRoles(roles);
        List<String> permissions = new ArrayList<>();
        user.getRoles().forEach(role -> role.getPermissions().forEach(permission -> permissions.add(permission.getCode())));
        userinfo.setPermissions(permissions.stream().distinct().toList());
        return userinfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "session", key = "#userId")
    public User saveRole(Long userId, List<Long> roleIds) {
        User user = getById(userId);
        roleService.getByIds(roleIds).forEach(role -> user.getRoles().add(role));
        return save(user);
    }

}