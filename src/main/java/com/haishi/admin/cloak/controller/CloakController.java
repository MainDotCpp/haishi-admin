package com.haishi.admin.cloak.controller;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.service.CloakService;
import com.haishi.admin.common.dto.HttpResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cloak", description = "斗篷")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cloak")
public class CloakController {

    private final CloakService cloakService;

    @Operation(summary = "调用斗篷")
    @PostMapping("/check/{key}")
    public HttpResult check(@PathVariable String key, @RequestBody CloakCheckDTO dto) {
        CloakCheckResult result = cloakService.check(key, dto);
        return HttpResult.success(result);
    }
}
