package com.haishi.admin.cloak.controller;

import com.haishi.admin.cloak.dto.CloakLogQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.service.CloakLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cloakLog", description = "访问记录控制器")
@RestController
@RequestMapping("cloakLog")
@AllArgsConstructor
public class CloakLogController {
    private final CloakLogService cloakLogService;

    @Operation(summary = "根据ID获取访问记录")
    @GetMapping("/getById")
    public HttpResult get(Long id) {
        CloakLog cloakLog = cloakLogService.getById(id);
        return HttpResult.success(cloakLog);
    }

    @Operation(summary = "分页查询访问记录")
    @GetMapping("/page")
    public HttpResult page(CloakLogQueryDTO queryDTO) {
        return HttpResult.success(
                cloakLogService.page(queryDTO)
        );
    }

    @Operation(summary = "保存访问记录")
    @PostMapping("/save")
    public HttpResult save(@RequestBody CloakLog cloakLog) {
        return HttpResult.success(
                cloakLogService.save(cloakLog)
        );
    }

    @Operation(summary = "删除访问记录")
    @PostMapping("/deleteById")
    public HttpResult deleteById(Long id) {
        return HttpResult.success(
                cloakLogService.delete(id)
        );
    }

}