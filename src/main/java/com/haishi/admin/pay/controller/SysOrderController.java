package com.haishi.admin.pay.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.pay.dto.AlipayNotifyDTO;
import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.pay.service.SysOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Tag(name = "sysOrder", description = "系统订单控制器")
@RestController
@RequestMapping("/sysOrder")
@AllArgsConstructor
public class SysOrderController {
    private final SysOrderService sysOrderService;

    @Operation(summary = "根据ID获取系统订单")
    @GetMapping("/getById")
    public HttpResult<SysOrderDTO> get(Long id) {
        return HttpResult.success(sysOrderService.getById(id));
    }

    @Operation(summary = "系统订单列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SYS_ORDER')")
    public HttpResult<List<SysOrderDTO>> list(SysOrderDTO queryDTO) {
        return HttpResult.success(
                sysOrderService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询系统订单")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('SYS_ORDER')")
    public HttpResult<PageDTO<SysOrderDTO>> page(SysOrderDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                sysOrderService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存系统订单")
    @PostMapping("/save")
    public HttpResult<SysOrderDTO> save(@RequestBody SysOrderDTO sysOrderUpdateDTO) {
        return HttpResult.success(
                sysOrderService.save(sysOrderUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除系统订单")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('SYS_ORDER__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                sysOrderService.delete(id)
        );
    }

    @Operation(summary = "支付回调 ")
    @RequestMapping("/payCallback")
    public String payCallback(@RequestParam HashMap<String, Object> notifyDTO) {
        return sysOrderService.payCallback(notifyDTO);

    }

}