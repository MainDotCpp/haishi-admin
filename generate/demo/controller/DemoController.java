package com.haishi.admin.demo.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.demo.dto.DemoDTO;
import com.haishi.admin.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "demo", description = "测试控制器")
@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @Operation(summary = "根据ID获取测试")
    @GetMapping("/getById")
    public HttpResult<DemoDTO> get(Long id) {
        return HttpResult.success(demoService.getById(id));
    }

    @Operation(summary = "测试列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('DEMO')")
    public HttpResult<List<DemoDTO>> list(DemoDTO queryDTO) {
        return HttpResult.success(
                demoService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询测试")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('DEMO')")
    public HttpResult<PageDTO<DemoDTO>> page(DemoDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                demoService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存测试")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('DEMO__EDIT')")
    public HttpResult<DemoDTO> save(@RequestBody DemoDTO demoUpdateDTO) {
        return HttpResult.success(
                demoService.save(demoUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除测试")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('DEMO__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                demoService.delete(id)
        );
    }

}