package com.haishi.admin.cloak.controller;

import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.service.cloakLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cloakLog", description = "访问记录控制器")
@RestController
@RequestMapping("/cloakLog")
@AllArgsConstructor
public class cloakLogController {
    private final cloakLogService cloakLogService;

    @Operation(summary = "根据ID获取访问记录")
    @GetMapping("/getById")
    public HttpResult get(Long id) {
        CloakLog cloakLog = cloakLogService.getById(id);
        return HttpResult.success(cloakLog);
    }

    @Operation(summary = "分页查询访问记录")
    @GetMapping("/page")
    public HttpResult page(PageDTO<CloakLog> pageDTO) {
        return HttpResult.success(
                cloakLogService.getPage(pageDTO)
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
    @PostMapping("/delete")
    public HttpResult delete(Long id) {
        return HttpResult.success(
                cloakLogService.delete(id)
        );
    }

}