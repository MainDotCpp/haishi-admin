package com.haishi.admin.system.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dto.RoleQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.entity.Role;
import com.haishi.admin.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "role", description = "角色控制器")
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "根据ID获取角色")
    @GetMapping("/getById")
    public HttpResult<Role> get(Long id) {
        Role role = roleService.getById(id);
        return HttpResult.success(role);
    }

    @Operation(summary = "角色列表")
    @GetMapping("/list")
    public HttpResult<List<Role>> list(RoleQueryDTO queryDTO) {
        return HttpResult.success(
                roleService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    public HttpResult<PageDTO<Role>> page(RoleQueryDTO queryDTO) {
        return HttpResult.success(
                roleService.page(queryDTO)
        );
    }

    @Operation(summary = "保存角色")
    @PostMapping("/save")
    public HttpResult<Role> save(@RequestBody Role role) {
        return HttpResult.success(
                roleService.save(role)
        );
    }

    @Operation(summary = "通过ID删除角色")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                roleService.delete(id)
        );
    }



}