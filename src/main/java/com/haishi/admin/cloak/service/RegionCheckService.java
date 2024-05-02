package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegionCheckService extends CloakCheckHandleIntercept {

    @Override
    String checkName() {
        return "地区检测";
    }

    @Override
    protected boolean isEnable(CloakLog cloakLog, CloakConfig cloakConfig) {
        return cloakConfig.getEnableRegionDetection();
    }

    @Override
    public CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig) {
        String allowRegion = cloakConfig.getAllowRegion();
        String countryCode = cloakLog.getCountryCode();

        log.info("[CLOAK] 地区检测 ALLOW:{} 当前地区:{}", allowRegion, countryCode);
        if (!allowRegion.contains(countryCode)) {
            return CheckStatus.FORBID_BY_REGION;
        }
        return null;
    }
}
