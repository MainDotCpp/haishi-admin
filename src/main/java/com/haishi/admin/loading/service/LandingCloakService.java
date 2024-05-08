package com.haishi.admin.loading.service;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.service.CloakService;
import com.haishi.admin.loading.dto.LandingCloakCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LandingCloakService {

    private final CloakService cloakService;

    public CloakCheckResult check(LandingCloakCheckDTO dto) {
        return cloakService.check(dto.cloakKey(), dto.clientInfo(), cloakLog -> {
            cloakLog.setScene(CloakScene.LANDING_PAGE);
            cloakLog.setRelatedId(dto.landingId());
            return cloakLog;
        });
    }
}
