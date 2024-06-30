package com.haishi.admin.system.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.system.dto.SystemConfigDTO;
import com.haishi.admin.system.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "systemConfig", description = "系统设置控制器")
@RestController
@RequestMapping("/systemConfig")
@AllArgsConstructor
public class SystemConfigController {
    private final SystemConfigService systemConfigService;

    @Operation(summary = "根据ID获取系统设置")
    @GetMapping("/getById")
    public HttpResult<SystemConfigDTO> get(Long id) {
        return HttpResult.success(systemConfigService.getById(id));
    }

    @Operation(summary = "系统设置列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SYSTEM_CONFIG')")
    public HttpResult<List<SystemConfigDTO>> list(SystemConfigDTO queryDTO) {
        return HttpResult.success(
                systemConfigService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询系统设置")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('SYSTEM_CONFIG')")
    public HttpResult<PageDTO<SystemConfigDTO>> page(SystemConfigDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                systemConfigService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存系统设置")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('SYSTEM_CONFIG__EDIT')")
    public HttpResult<SystemConfigDTO> save(@RequestBody SystemConfigDTO systemConfigUpdateDTO) {
        return HttpResult.success(
                systemConfigService.save(systemConfigUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除系统设置")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('SYSTEM_CONFIG__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                systemConfigService.delete(id)
        );
    }

}