package com.haishi.admin.system.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dto.PermissionQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.entity.Permission;
import com.haishi.admin.system.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "permission", description = "权限控制器")
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @Operation(summary = "根据ID获取权限")
    @GetMapping("/getById")
    public HttpResult<Permission> get(Long id) {
        Permission permission = permissionService.getById(id);
        return HttpResult.success(permission);
    }

    @Operation(summary = "权限列表")
    @GetMapping("/list")
    public HttpResult<List<Permission>> list(PermissionQueryDTO queryDTO) {
        return HttpResult.success(
                permissionService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询权限")
    @GetMapping("/page")
    public HttpResult<PageDTO<Permission>> page(PermissionQueryDTO queryDTO) {
        return HttpResult.success(
                permissionService.page(queryDTO)
        );
    }

    @Operation(summary = "保存权限")
    @PostMapping("/save")
    public HttpResult<Permission> save(@RequestBody Permission permission) {
        return HttpResult.success(
                permissionService.save(permission)
        );
    }

    @Operation(summary = "通过ID删除权限")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                permissionService.delete(id)
        );
    }

}