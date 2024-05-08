package com.haishi.admin.loading.controller;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.common.dto.HttpResult;
import com.haishi.admin.loading.dto.LandingCloakCheckDTO;
import com.haishi.admin.loading.service.LandingCloakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LandingCloak", description = "落地页斗篷")
@RequestMapping("/landing/cloak")
@RestController
@RequiredArgsConstructor
public class LandingCloakController {

    private final LandingCloakService landingCloakService;


    @Operation(summary = "检查落地页斗篷")
    @PostMapping("/check")
    public HttpResult<CloakCheckResult> check(@RequestBody LandingCloakCheckDTO dto) {
        CloakCheckResult result = landingCloakService.check(dto);
        return HttpResult.success(result);
    }


}
