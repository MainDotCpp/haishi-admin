package com.haishi.admin.common.aspect;

import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.system.dto.Userinfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

@Slf4j
@Aspect
@Component
public class ControllerLogAdvice {

    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void operation() {
    }

    @Around("operation()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String requestURI = request.getRequestURI();
        // 获取 Operation 注解的value
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Operation operation = signature.getMethod().getAnnotation(Operation.class);
        Userinfo userinfo = ThreadUserinfo.get();
        log.info("[{}]{} ({}|{})", operation.summary(), requestURI, userinfo.getId(), userinfo.getNickname());
        return pjp.proceed();
    }
}
