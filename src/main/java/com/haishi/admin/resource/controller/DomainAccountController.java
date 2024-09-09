package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.DomainAccountDTO;
import com.haishi.admin.resource.dto.GoddyDomainDTO;
import com.haishi.admin.resource.service.DomainAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "domainAccount", description = "域名账户控制器")
@RestController
@RequestMapping("/domainAccount")
@AllArgsConstructor
public class DomainAccountController {
    private final DomainAccountService domainAccountService;

    @Operation(summary = "根据ID获取域名账户")
    @GetMapping("/getById")
    public HttpResult<DomainAccountDTO> get(Long id) {
        return HttpResult.success(domainAccountService.getById(id));
    }

    @Operation(summary = "域名账户列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('DOMAIN_ACCOUNT','DOMAIN')")
    public HttpResult<List<DomainAccountDTO>> list(DomainAccountDTO queryDTO) {
        return HttpResult.success(
                domainAccountService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询域名账户")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('DOMAIN_ACCOUNT')")
    public HttpResult<PageDTO<DomainAccountDTO>> page(DomainAccountDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                domainAccountService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存域名账户")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('DOMAIN_ACCOUNT__EDIT')")
    public HttpResult<DomainAccountDTO> save(@RequestBody DomainAccountDTO domainAccountUpdateDTO) {
        return HttpResult.success(
                domainAccountService.save(domainAccountUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除域名账户")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('DOMAIN_ACCOUNT__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                domainAccountService.delete(id)
        );
    }

    @Operation(summary = "获取域名账户列表")
    @GetMapping("/domainList")
    public HttpResult<List<GoddyDomainDTO>> domainList(Long accountId) {
        return HttpResult.success(
                domainAccountService.getDomainList(accountId)
        );
    }


}