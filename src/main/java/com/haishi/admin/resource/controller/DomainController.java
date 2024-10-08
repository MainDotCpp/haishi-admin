package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.DomainAgentConfig;
import com.haishi.admin.resource.service.DomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "domain", description = "域名控制器")
@RestController
@RequestMapping("/domain")
@AllArgsConstructor
public class DomainController {
    private final DomainService domainService;

    @Operation(summary = "根据ID获取域名")
    @GetMapping("/getById")
    public HttpResult<DomainDTO> get(Long id) {
        return HttpResult.success(domainService.getById(id));
    }

    @Operation(summary = "域名列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('DOMAIN')")
    public HttpResult<List<DomainDTO>> list(DomainDTO queryDTO) {
        return HttpResult.success(
                domainService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询域名")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('DOMAIN')")
    public HttpResult<PageDTO<DomainDTO>> page(DomainDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                domainService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存域名")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('DOMAIN__EDIT')")
    public HttpResult<DomainDTO> save(@RequestBody DomainDTO domainUpdateDTO) {
        return HttpResult.success(
                domainService.save(domainUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除域名")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('DOMAIN__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                domainService.delete(id)
        );
    }


    @Operation(summary = "获取AGENT域名配置文件")
    @GetMapping("/getAgentConfig")
    public DomainAgentConfig getAgentConfig(Long id) {
        return domainService.getAgentConfig(id);
    }


    @Operation(summary = "部署域名")
    @PostMapping("/deploy")
    public HttpResult<String> deploy(Long id) {
        domainService.deploy(id);
        return HttpResult.success("部署成功");
    }

    @Operation(summary = "领取域名")
    @PostMapping("/receive")
    public HttpResult<String> receive(Long id) {
        domainService.receive(id);
        return HttpResult.success("领取成功");
    }

    @Operation(summary = "配置SSL")
    @PostMapping("/configSsl")
    public HttpResult<Boolean> configSsl(Long id) {
        return HttpResult.success(
                domainService.configSsl(id)
        );
    }
}