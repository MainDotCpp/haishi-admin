package com.haishi.admin.shortlink.controller;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.shortlink.dto.ShortLinkGroupQueryDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.shortlink.entity.ShortLinkGroup;
import com.haishi.admin.shortlink.service.ShortLinkGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "shortLinkGroup", description = "短链接组控制器")
@RestController
@RequestMapping("/shortLinkGroup")
@AllArgsConstructor
public class ShortLinkGroupController {
    private final ShortLinkGroupService shortLinkGroupService;

    @Operation(summary = "根据ID获取短链接组")
    @GetMapping("/getById")
    public HttpResult<ShortLinkGroup> get(Long id) {
        ShortLinkGroup shortLinkGroup = shortLinkGroupService.getById(id);
        return HttpResult.success(shortLinkGroup);
    }

    @Operation(summary = "短链接组列表")
    @GetMapping("/list")
    public HttpResult<List<ShortLinkGroup>> list(ShortLinkGroupQueryDTO queryDTO) {
        return HttpResult.success(
                shortLinkGroupService.list(queryDTO)
        );
    }

    @Operation(summary = "分页查询短链接组")
    @GetMapping("/page")
    public HttpResult<PageDTO<ShortLinkGroup>> page(ShortLinkGroupQueryDTO queryDTO) {
        return HttpResult.success(
                shortLinkGroupService.page(queryDTO)
        );
    }

    @Operation(summary = "保存短链接组")
    @PostMapping("/save")
    public HttpResult<ShortLinkGroup> save(@RequestBody ShortLinkGroup shortLinkGroup) {
        return HttpResult.success(
                shortLinkGroupService.save(shortLinkGroup)
        );
    }

    @Operation(summary = "通过ID删除短链接组")
    @PostMapping("/deleteById")
    public HttpResult<Boolean> delete(Long id) {
        return HttpResult.success(
                shortLinkGroupService.delete(id)
        );
    }

}