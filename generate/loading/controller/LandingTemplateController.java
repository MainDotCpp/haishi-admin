package com.haishi.admin.loading.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.loading.dto.LandingTemplateQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.loading.entity.LandingTemplate;
import com.haishi.admin.loading.service.LandingTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "landingTemplate", description = "落地页模板控制器")
@RestController
@RequestMapping("/landingTemplate")
@AllArgsConstructor
public class LandingTemplateController {
    private final LandingTemplateService landingTemplateService;

    @Operation(summary = "根据ID获取落地页模板")
    @GetMapping("/getById")
    public HttpResult<LandingTemplate> get(Long id) {
        LandingTemplate landingTemplate = landingTemplateService.getById(id);
        return HttpResult.success(landingTemplate);
    }

    @Operation(summary = "落地页模板列表")
    @GetMapping("/list")
    public HttpResult<List<LandingTemplate>> list(LandingTemplateQueryDTO queryDTO) {
        return HttpResult.success(
                landingTemplateService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询落地页模板")
    @GetMapping("/page")
    public HttpResult<PageDTO<LandingTemplate>> page(LandingTemplateQueryDTO queryDTO) {
        return HttpResult.success(
                landingTemplateService.page(queryDTO)
        );
    }

    @Operation(summary = "保存落地页模板")
    @PostMapping("/save")
    public HttpResult<LandingTemplate> save(@RequestBody LandingTemplate landingTemplate) {
        return HttpResult.success(
                landingTemplateService.save(landingTemplate)
        );
    }

    @Operation(summary = "通过ID删除落地页模板")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                landingTemplateService.delete(id)
        );
    }

}