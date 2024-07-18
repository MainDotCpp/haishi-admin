package com.haishi.admin.store.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.store.dto.CommodityOrderDTO;
import com.haishi.admin.store.dto.CreateCommodityOrderResponse;
import com.haishi.admin.store.service.CommodityOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "commodityOrder", description = "商品订单控制器")
@RestController
@RequestMapping("/commodityOrder")
@AllArgsConstructor
public class CommodityOrderController {
    private final CommodityOrderService commodityOrderService;

    @Operation(summary = "根据ID获取商品订单")
    @GetMapping("/getById")
    public HttpResult<CommodityOrderDTO> get(Long id) {
        return HttpResult.success(commodityOrderService.getById(id));
    }

    @Operation(summary = "商品订单列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ORDER')")
    public HttpResult<List<CommodityOrderDTO>> list(CommodityOrderDTO queryDTO) {
        return HttpResult.success(
                commodityOrderService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询商品订单")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ORDER')")
    public HttpResult<PageDTO<CommodityOrderDTO>> page(CommodityOrderDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                commodityOrderService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存商品订单")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ORDER__EDIT')")
    public HttpResult<CommodityOrderDTO> save(@RequestBody CommodityOrderDTO commodityOrderUpdateDTO) {
        return HttpResult.success(
                commodityOrderService.save(commodityOrderUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除商品订单")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('COMMODITY_ORDER__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                commodityOrderService.delete(id)
        );
    }

    @Operation(summary = "购买商品")
    @PostMapping("/pay")
    public HttpResult<CreateCommodityOrderResponse> pay(@RequestBody CommodityOrderDTO dto) {
        return HttpResult.success(
                commodityOrderService.pay(dto)
        );
    }

    @Operation(summary = "查询订单支付状态")
    @GetMapping("/queryPayStatus")
    public HttpResult<Boolean> queryPayStatus(String orderNo) {
        return HttpResult.success(
                commodityOrderService.queryPayStatus(orderNo)
        );
    }

    @Operation(summary = "查询订单内容")
    @PostMapping("/queryOrder")
    public HttpResult<CommodityOrderDTO> queryOrder(@RequestBody CommodityOrderDTO dto) {
        return HttpResult.success(
                commodityOrderService.queryOrder(dto)
        );
    }

}