package com.haishi.admin.store.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.store.dto.CommodityItemDTO;
import com.haishi.admin.store.service.CommodityItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "commodityItem", description = "商品库存控制器")
@RestController
@RequestMapping("/commodityItem")
@AllArgsConstructor
public class CommodityItemController {
    private final CommodityItemService commodityItemService;

    @Operation(summary = "根据ID获取商品库存")
    @GetMapping("/getById")
    public HttpResult<CommodityItemDTO> get(Long id) {
        return HttpResult.success(commodityItemService.getById(id));
    }

    @Operation(summary = "商品库存列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ITEM')")
    public HttpResult<List<CommodityItemDTO>> list(CommodityItemDTO queryDTO) {
        return HttpResult.success(
                commodityItemService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询商品库存")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ITEM')")
    public HttpResult<PageDTO<CommodityItemDTO>> page(CommodityItemDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                commodityItemService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存商品库存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ITEM__EDIT')")
    public HttpResult<CommodityItemDTO> save(@RequestBody CommodityItemDTO commodityItemUpdateDTO) {
        return HttpResult.success(
                commodityItemService.save(commodityItemUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除商品库存")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ITEM__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                commodityItemService.delete(id)
        );
    }

    @Operation(summary = "批量保存商品库存")
    @PostMapping("/batchSave")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ITEM__EDIT')")
    public HttpResult<Boolean> batchSave(@RequestBody List<CommodityItemDTO> commodityItemDTOList) {
        commodityItemService.batchSave(commodityItemDTOList);
        return HttpResult.success(true);
    }

}