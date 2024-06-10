package com.haishi.admin.resource.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.resource.dto.ServerDTO;
import com.haishi.admin.resource.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "server", description = "服务器控制器")
@RestController
@RequestMapping("/server")
@AllArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @Operation(summary = "根据ID获取服务器")
    @GetMapping("/getById")
    public HttpResult<ServerDTO> get(Long id) {
        return HttpResult.success(serverService.getById(id));
    }

    @Operation(summary = "服务器列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SERVER')")
    public HttpResult<List<ServerDTO>> list(ServerDTO queryDTO) {
        return HttpResult.success(
                serverService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询服务器")
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('SERVER')")
    public HttpResult<PageDTO<ServerDTO>> page(ServerDTO queryDTO, Integer current, Integer pageSize) {
        return HttpResult.success(
                serverService.page(queryDTO, current, pageSize)
        );
    }

    @Operation(summary = "保存服务器")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('SERVER__EDIT')")
    public HttpResult<ServerDTO> save(@RequestBody ServerDTO serverUpdateDTO) {
        return HttpResult.success(
                serverService.save(serverUpdateDTO)
        );
    }

    @Operation(summary = "通过ID删除服务器")
    @PostMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('SERVER__DELETE')")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                serverService.delete(id)
        );
    }

}