package com.haishi.admin.resource.controller;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.WebsiteCloakCheckDTO;
import com.haishi.admin.resource.dto.WebsiteDTO;
import com.haishi.admin.resource.service.WebsiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "website", description = "网站控制器")
@RestController
@RequestMapping("/website")
@AllArgsConstructor
public class WebsiteController {
    private final WebsiteService websiteService;

    @Operation(summary = "根据ID获取网站")
    @GetMapping("/getById")
    public HttpResult<WebsiteDTO> get(Long id) {
        return HttpResult.success(websiteService.getById(id));
    }

    @Operation(summary = "网站列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('WEBSITE')")
    public HttpResult<List<WebsiteDTO>> list(WebsiteDTO queryDTO) {
        return HttpResult.success(
                websiteService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询网站")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('WEBSITE')")
    public HttpResult<PageDTO<WebsiteDTO>> page(WebsiteDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                websiteService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存网站")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('WEBSITE__EDIT')")
    public HttpResult<WebsiteDTO> save(@RequestBody WebsiteDTO websiteUpdateDTO) {
        return HttpResult.success(
                websiteService.save(websiteUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除网站")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('WEBSITE__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                websiteService.delete(id)
        );
    }

    @Operation(summary = "检查站点斗篷")
    @PostMapping("/check")
    public HttpResult<CloakCheckResult> check(@RequestBody WebsiteCloakCheckDTO dto) {
        CloakCheckResult result = websiteService.check(dto);
        return HttpResult.success(result);
    }

}