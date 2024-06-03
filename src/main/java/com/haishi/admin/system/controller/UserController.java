package com.haishi.admin.system.controller;

import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dto.UserQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.dto.Userinfo;
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

    @Operation(summary = "设置用户角色")
    @PostMapping("/setRoles")
    public HttpResult<Boolean> setRoles(@RequestBody SetRoleDTO setRoleDTO) {
        userService.saveRole(setRoleDTO.userId(), setRoleDTO.roleIds());
        return HttpResult.success(true);
    }

    private static record SetRoleDTO(Long userId, List<Long> roleIds) {
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/getUserinfo")
    public HttpResult<Userinfo> getUserinfo() {
        return HttpResult.success(ThreadUserinfo.get());
    }
}