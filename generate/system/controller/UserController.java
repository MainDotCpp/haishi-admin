package com.haishi.admin.system.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dto.UserQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.entity.User;
import com.haishi.admin.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user", description = "用户控制器")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/getById")
    public HttpResult<User> get(Long id) {
        User user = userService.getById(id);
        return HttpResult.success(user);
    }

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public HttpResult<List<User>> list(UserQueryDTO queryDTO) {
        return HttpResult.success(
                userService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    public HttpResult<PageDTO<User>> page(UserQueryDTO queryDTO) {
        return HttpResult.success(
                userService.page(queryDTO)
        );
    }

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    public HttpResult<User> save(@RequestBody User user) {
        return HttpResult.success(
                userService.save(user)
        );
    }

    @Operation(summary = "通过ID删除用户")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                userService.delete(id)
        );
    }

}