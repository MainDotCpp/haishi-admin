package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.OrderDTO;
import com.haishi.admin.resource.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "order", description = "工单控制器")
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "根据ID获取工单")
    @GetMapping("/getById")
    public HttpResult<OrderDTO> get(Long id) {
        return HttpResult.success(orderService.getById(id));
    }

    @Operation(summary = "工单列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ORDER')")
    public HttpResult<List<OrderDTO>> list(OrderDTO queryDTO) {
        return HttpResult.success(
                orderService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询工单")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('ORDER')")
    public HttpResult<PageDTO<OrderDTO>> page(OrderDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                orderService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存工单")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ORDER__EDIT')")
    public HttpResult<OrderDTO> save(@RequestBody OrderDTO orderUpdateDTO) {
        return HttpResult.success(
                orderService.save(orderUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除工单")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('ORDER__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                orderService.delete(id)
        );
    }

}