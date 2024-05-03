package com.haishi.admin.cloak.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.dto.BlacklistIpQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.cloak.entity.BlacklistIp;
import com.haishi.admin.cloak.service.BlacklistIpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "blacklistIp", description = "黑名单 ip控制器")
@RestController
@RequestMapping("/blacklistIp")
@AllArgsConstructor
public class BlacklistIpController {
    private final BlacklistIpService blacklistIpService;

    @Operation(summary = "根据ID获取黑名单 ip")
    @GetMapping("/getById")
    public HttpResult<BlacklistIp> get(Long id) {
        BlacklistIp blacklistIp = blacklistIpService.getById(id);
        return HttpResult.success(blacklistIp);
    }

    @Operation(summary = "黑名单 ip列表")
    @GetMapping("/list")
    public HttpResult<List<BlacklistIp>> list(BlacklistIpQueryDTO queryDTO) {
        return HttpResult.success(
                blacklistIpService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询黑名单 ip")
    @GetMapping("/page")
    public HttpResult<PageDTO<BlacklistIp>> page(BlacklistIpQueryDTO queryDTO) {
        return HttpResult.success(
                blacklistIpService.page(queryDTO)
        );
    }

    @Operation(summary = "保存黑名单 ip")
    @PostMapping("/save")
    public HttpResult<BlacklistIp> save(@RequestBody BlacklistIp blacklistIp) {
        return HttpResult.success(
                blacklistIpService.save(blacklistIp)
        );
    }

    @Operation(summary = "通过ID删除黑名单 ip")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                blacklistIpService.delete(id)
        );
    }

}