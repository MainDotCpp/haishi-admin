package com.haishi.admin.store.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.store.dto.CommodityGroupDTO;
import com.haishi.admin.store.service.CommodityGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "commodityGroup", description = "商品分组控制器")
@RestController
@RequestMapping("/commodityGroup")
@AllArgsConstructor
public class CommodityGroupController {
    private final CommodityGroupService commodityGroupService;

    @Operation(summary = "根据ID获取商品分组")
    @GetMapping("/getById")
    public HttpResult<CommodityGroupDTO> get(Long id) {
        return HttpResult.success(commodityGroupService.getById(id));
    }

    @Operation(summary = "商品分组列表")
    @GetMapping("/list")
    public HttpResult<List<CommodityGroupDTO>> list(CommodityGroupDTO queryDTO) {
        return HttpResult.success(
                commodityGroupService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询商品分组")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('COMMODITY_GROUP')")
    public HttpResult<PageDTO<CommodityGroupDTO>> page(CommodityGroupDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                commodityGroupService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存商品分组")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('COMMODITY_GROUP__EDIT')")
    public HttpResult<CommodityGroupDTO> save(@RequestBody CommodityGroupDTO commodityGroupUpdateDTO) {
        return HttpResult.success(
                commodityGroupService.save(commodityGroupUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除商品分组")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('COMMODITY_GROUP__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                commodityGroupService.delete(id)
        );
    }

}