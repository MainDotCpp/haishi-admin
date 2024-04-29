package com.haishi.admin.cloak.controller;

import com.haishi.admin.cloak.dto.CloakConfigQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.service.CloakConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cloakConfig", description = "斗篷配置 控制器")
@RestController
@RequestMapping("/cloakConfig")
@AllArgsConstructor
public class CloakConfigController {
    private final CloakConfigService cloakConfigService;

    @Operation(summary = "根据ID获取斗篷配置 ")
    @GetMapping("/getById")
    public HttpResult get(Long id) {
        CloakConfig cloakConfig = cloakConfigService.getById(id);
        return HttpResult.success(cloakConfig);
    }

    @Operation(summary = "分页查询斗篷配置 ")
    @GetMapping("/page")
    public HttpResult page(CloakConfigQueryDTO queryDTO) {
        return HttpResult.success(
                cloakConfigService.page(queryDTO)
        );
    }

    @Operation(summary = "保存斗篷配置 ")
    @PostMapping("/save")
    public HttpResult save(@RequestBody CloakConfig cloakConfig) {
        return HttpResult.success(
                cloakConfigService.save(cloakConfig)
        );
    }

    @Operation(summary = "通过ID删除斗篷配置 ")
    @PostMapping("/deleteById")
    public HttpResult delete(Long id) {
        return HttpResult.success(
                cloakConfigService.delete(id)
        );
    }

}