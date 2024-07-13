package com.haishi.admin.store.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.store.dto.CommodityDTO;
import com.haishi.admin.store.service.CommodityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "commodity", description = "商品控制器")
@RestController
@RequestMapping("/commodity")
@AllArgsConstructor
public class CommodityController {
    private final CommodityService commodityService;

    @Operation(summary = "根据ID获取商品")
    @GetMapping("/getById")
    public HttpResult<CommodityDTO> get(Long id) {
        return HttpResult.success(commodityService.getById(id));
    }

    @Operation(summary = "商品列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('COMMODITY')")
    public HttpResult<List<CommodityDTO>> list(CommodityDTO queryDTO) {
        return HttpResult.success(
                commodityService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询商品")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('COMMODITY')")
    public HttpResult<PageDTO<CommodityDTO>> page(CommodityDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                commodityService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存商品")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('COMMODITY__EDIT')")
    public HttpResult<CommodityDTO> save(@RequestBody CommodityDTO commodityUpdateDTO) {
        return HttpResult.success(
                commodityService.save(commodityUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除商品")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('COMMODITY__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                commodityService.delete(id)
        );
    }

}