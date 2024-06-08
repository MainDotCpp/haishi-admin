package com.haishi.admin.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dto.DeptQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.entity.Dept;
import com.haishi.admin.system.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "dept", description = "部门控制器")
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @Operation(summary = "根据ID获取部门")
    @GetMapping("/getById")
    public HttpResult<Dept> get(Long id) {
        Dept dept = deptService.getById(id);
        return HttpResult.success(dept);
    }

    @Operation(summary = "部门列表")
    @GetMapping("/list")
    public HttpResult<List<Dept>> list(DeptQueryDTO queryDTO) {
        return HttpResult.success(
                deptService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询部门")
    @GetMapping("/page")
    public HttpResult<PageDTO<Dept>> page(DeptQueryDTO queryDTO) {
        return HttpResult.success(
                deptService.page(queryDTO)
        );
    }

    @Operation(summary = "保存部门")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('DEPT__EDIT')")
    public HttpResult<Dept> save(@RequestBody Dept dept) {
        return HttpResult.success(
                deptService.save(dept)
        );
    }

    @Operation(summary = "通过ID删除部门")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('DEPT__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                deptService.delete(id)
        );
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/getDeptTree")
    public HttpResult<Tree<String>> getDeptTree() {
        return HttpResult.success(
                deptService.getDeptTree()
        );
    }

}