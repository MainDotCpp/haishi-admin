package com.haishi.admin.shortlink.controller;

import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import com.haishi.admin.shortlink.service.ShortLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "shortlink", description = "Short Link Controller")
@RestController
@RequestMapping("shortlink")
@AllArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @Operation(summary = "根据ID获取短链接配置")
    @GetMapping("/getById")
    public HttpResult<ShortLinkConfig> get(Long id) {
        ShortLinkConfig shortLinkConfig = shortLinkService.getById(id);
        return HttpResult.success(shortLinkConfig);
    }

    @Operation(summary = "根据KEY获取短链接")
    @GetMapping("/getByKey")
    public HttpResult<ShortLinkConfig> getByKey(String key) {
        ShortLinkConfig shortLinkConfig = shortLinkService.getByKey(key);
        return HttpResult.success(shortLinkConfig);
    }

    @Operation(summary = "分页查询短链接配置")
    @GetMapping("/page")
    public HttpResult<PageDTO<ShortLinkConfig>> page(PageDTO<ShortLinkConfig> pageDTO) {
        return HttpResult.success(
                shortLinkService.getPage(pageDTO)
        );
    }

    @Operation(summary = "保存短链接配置")
    @PostMapping("/save")
    public HttpResult<ShortLinkConfig> save(@RequestBody ShortLinkConfig shortLinkConfig) {
        return HttpResult.success(
                shortLinkService.save(shortLinkConfig)
        );
    }

    @Operation(summary = "删除短链接配置")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                shortLinkService.delete(id)
        );
    }

}
