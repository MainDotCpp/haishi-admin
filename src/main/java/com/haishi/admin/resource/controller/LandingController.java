package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.LandingDTO;
import com.haishi.admin.resource.service.LandingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "landing", description = "落地页控制器")
@RestController
@RequestMapping("/landing")
@AllArgsConstructor
public class LandingController {
    private final LandingService landingService;

    @Operation(summary = "根据ID获取落地页")
    @GetMapping("/getById")
    public HttpResult<LandingDTO> get(Long id) {
        return HttpResult.success(landingService.getById(id));
    }

    @Operation(summary = "落地页列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('LANDING')")
    public HttpResult<List<LandingDTO>> list(LandingDTO queryDTO) {
        return HttpResult.success(
                landingService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询落地页")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('LANDING')")
    public HttpResult<PageDTO<LandingDTO>> page(LandingDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                landingService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存落地页")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('LANDING__EDIT')")
    public HttpResult<LandingDTO> save(@RequestBody LandingDTO landingUpdateDTO) {
        return HttpResult.success(
                landingService.save(landingUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除落地页")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('LANDING__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                landingService.delete(id)
        );
    }


    public record SaveByUrlDTO(String url) {
    }

    @Operation(summary = "通过URL保存落地页")
    @PostMapping("saveByUrl")
    @PreAuthorize("hasAnyAuthority('LANDING__EDIT')")
    public HttpResult<Boolean> saveByUrl(@RequestBody SaveByUrlDTO dto) {
        return HttpResult.success(landingService.downloadWeb(dto.url));
    }

}