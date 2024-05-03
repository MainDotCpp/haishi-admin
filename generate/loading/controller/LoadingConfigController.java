package com.haishi.admin.loading.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.loading.dto.LoadingConfigQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.loading.entity.LoadingConfig;
import com.haishi.admin.loading.service.LoadingConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "loadingConfig", description = "落地页配置控制器")
@RestController
@RequestMapping("/loadingConfig")
@AllArgsConstructor
public class LoadingConfigController {
    private final LoadingConfigService loadingConfigService;

    @Operation(summary = "根据ID获取落地页配置")
    @GetMapping("/getById")
    public HttpResult<LoadingConfig> get(Long id) {
        LoadingConfig loadingConfig = loadingConfigService.getById(id);
        return HttpResult.success(loadingConfig);
    }

    @Operation(summary = "根据key获取落地页配置")
    @GetMapping("/getByKey")
    public HttpResult<LoadingConfig> getByKey(String path) {
        LoadingConfig loadingConfig = loadingConfigService.getByKey(path);
        return HttpResult.success(loadingConfig);
    }

    @Operation(summary = "落地页配置列表")
    @GetMapping("/list")
    public HttpResult<List<LoadingConfig>> list(LoadingConfigQueryDTO queryDTO) {
        return HttpResult.success(
                loadingConfigService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询落地页配置")
    @GetMapping("/page")
    public HttpResult<PageDTO<LoadingConfig>> page(LoadingConfigQueryDTO queryDTO) {
        return HttpResult.success(
                loadingConfigService.page(queryDTO)
        );
    }

    @Operation(summary = "保存落地页配置")
    @PostMapping("/save")
    public HttpResult<LoadingConfig> save(@RequestBody LoadingConfig loadingConfig) {
        return HttpResult.success(
                loadingConfigService.save(loadingConfig)
        );
    }

    @Operation(summary = "通过ID删除落地页配置")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                loadingConfigService.delete(id)
        );
    }

}