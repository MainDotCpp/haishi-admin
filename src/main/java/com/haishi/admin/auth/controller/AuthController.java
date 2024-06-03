package com.haishi.admin.auth.controller;

import com.haishi.admin.auth.dto.LoginReqDTO;
import com.haishi.admin.auth.service.AuthService;
import com.haishi.admin.common.dto.HttpResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "认证")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public HttpResult<String> login(@RequestBody LoginReqDTO loginReqDTO) {
        String token = authService.login(loginReqDTO);
        return HttpResult.success(token);
    }

}
