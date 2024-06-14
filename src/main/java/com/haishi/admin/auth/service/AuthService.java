package com.haishi.admin.auth.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.auth.dto.LoginReqDTO;
import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import com.haishi.admin.system.dto.Userinfo;
import com.haishi.admin.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthService implements UserDetailsService {
    @Resource
    private PasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;


    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        RBucket<Long> tokenBucket = redissonClient.getBucket("token:" + accessToken);
        if (!tokenBucket.isExists()) throw new BizException(BizExceptionEnum.USER_NOT_LOGIN);
        if (tokenBucket.get() == null) throw new BizException(BizExceptionEnum.USER_NOT_FOUND);
        Userinfo userinfo;
        try {
            userinfo = userService.getUserinfoByUserId(tokenBucket.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(BizExceptionEnum.USER_NOT_FOUND);
        }

        ThreadUserinfo.set(userinfo);

        // 从redis获取用户信息和权限信息
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : userinfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        for (String permission : userinfo.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        return new User(accessToken, bCryptPasswordEncoder.encode("access"), authorities);
    }

    public String login(String username, String password) {
        // 通过用户名和密码获取用户信息
        com.haishi.admin.system.entity.User user = userService.getUserByUserName(username);
        if (user == null) throw new BizException(BizExceptionEnum.USER_NOT_FOUND);
        if (user.getStatus() == 0) throw new BizException(BizExceptionEnum.USER_FORBIDDEN);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);

        log.info("用户登录成功: {} {}", user.getId(), user.getUsername());

        // 退出登录
        logout(user.getId());


        String token = IdUtil.fastUUID();
        RBucket<Long> tokenBucket = redissonClient.getBucket("token:" + token);
        tokenBucket.set(user.getId(), 7, TimeUnit.DAYS);
        user.setAccessToken(token);
        userService.save(user);
        return token;
    }

    public String login(LoginReqDTO loginReqDTO) {
        return login(loginReqDTO.username(), loginReqDTO.password());
    }

    public boolean logout(String accessToken) {
        Long userId = (Long) redissonClient.getBucket("token:" + accessToken).getAndDelete();
        clearSession(userId);
        return true;
    }

    public boolean logout() {
        return logout(ThreadUserinfo.get().getId());
    }

    public boolean logout(Long userId) {
        com.haishi.admin.system.entity.User user = userService.getById(userId);
        logout(user.getAccessToken());
        user.setAccessToken(null);
        userService.save(user);
        return true;
    }

    public void clearSession(Long userId) {
        if (userId == null) return;
        try {
            redissonClient.getMap("session").remove(userId);
        } catch (Exception e) {
            log.error("用户缓存不存在");
        }
    }

    public void clearSession(String accessToken) {
        Long userId = (Long) redissonClient.getBucket("token:" + accessToken).get();
        clearSession(userId);
    }
}
