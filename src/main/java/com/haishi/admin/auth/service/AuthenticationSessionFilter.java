package com.haishi.admin.auth.service;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import com.haishi.admin.system.dto.Userinfo;
import com.haishi.admin.system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationSessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 通过请求头中的token获取用户信息
        try {

            String accessToken = request.getHeader("Access-Token");

            if (accessToken == null) {
                throwError(response);
                return;
            }

            // 查询redis中是否存在该用户的token
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(accessToken, "access");
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            log.info("AuthenticationSessionFilter error", e);
            throwError(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private static void throwError(HttpServletResponse response) {
        response.setStatus(200);
        HttpResult<Object> result = HttpResult.error(new BizException(BizExceptionEnum.USER_NOT_LOGIN));
        ObjectMapper om = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(om.writeValueAsString(result));
        } catch (IOException e) {
            log.info("AuthenticationSessionFilter error", e);
        }
    }
}
