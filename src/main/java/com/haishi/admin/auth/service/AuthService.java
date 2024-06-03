package com.haishi.admin.auth.service;

import cn.hutool.jwt.JWTUtil;
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
        Long userId = (Long) redissonClient.getBucket("token:" + accessToken).get();
        Userinfo userinfo = userService.getUserinfoByUserId(userId);
        ThreadUserinfo.set(userinfo);

        // 从redis获取用户信息和权限信息
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : userinfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        for (String permission : userinfo.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        return new User(accessToken, bCryptPasswordEncoder.encode(userinfo.getUsername()), authorities);
    }

    public String login(String username, String password) {
        // 通过用户名和密码获取用户信息
        com.haishi.admin.system.entity.User user = userService.getUserByUserName(username);
        if (user == null) throw new BizException(BizExceptionEnum.USER_NOT_FOUND);
        if (user.getStatus() == 0) throw new BizException(BizExceptionEnum.USER_FORBIDDEN);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);

        // 登录成功, 生成token
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("uid", user.getId());
        payload.put("username", username);
        payload.put("exp", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String token = JWTUtil.createToken(payload, "haishi".getBytes());
        RBucket<Object> tokenBucket = redissonClient.getBucket("token:" + token);
        tokenBucket.set(user.getId());
        tokenBucket.expire(Duration.of(7, ChronoUnit.DAYS));
        return token;
    }

    public String login(LoginReqDTO loginReqDTO) {
        return login(loginReqDTO.username(), loginReqDTO.password());
    }
}
