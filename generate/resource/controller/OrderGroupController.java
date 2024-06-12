package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.OrderGroupDTO;
import com.haishi.admin.resource.service.OrderGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "orderGroup", description = "工单组控制器")
@RestController
@RequestMapping("/orderGroup")
@AllArgsConstructor
public class OrderGroupController {
    private final OrderGroupService orderGroupService;

    @Operation(summary = "根据ID获取工单组")
    @GetMapping("/getById")
    public HttpResult<OrderGroupDTO> get(Long id) {
        return HttpResult.success(orderGroupService.getById(id));
    }

    @Operation(summary = "工单组列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ORDER_GROUP')")
    public HttpResult<List<OrderGroupDTO>> list(OrderGroupDTO queryDTO) {
        return HttpResult.success(
                orderGroupService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询工单组")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('ORDER_GROUP')")
    public HttpResult<PageDTO<OrderGroupDTO>> page(OrderGroupDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                orderGroupService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存工单组")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ORDER_GROUP__EDIT')")
    public HttpResult<OrderGroupDTO> save(@RequestBody OrderGroupDTO orderGroupUpdateDTO) {
        return HttpResult.success(
                orderGroupService.save(orderGroupUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除工单组")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('ORDER_GROUP__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                orderGroupService.delete(id)
        );
    }

}