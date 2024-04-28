package com.haishi.admin.cloak.service;


import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UaCheckService extends CloakCheckHandleIntercept {
    @Override
    String checkName() {
        return "UA检测";
    }

    @Override
    public CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig) {
        String userAgent = cloakLog.getUserAgent();
        UserAgent ua = UserAgentUtil.parse(userAgent);

        log.info("[CLOAK] 检测 UA: {}", userAgent);
        cloakLog.setPlatform(ua.getPlatform().getName());
        cloakLog.setOs(ua.getOs().getName());
        cloakLog.setBrowser(ua.getBrowser().getName());
        cloakLog.setEngine(ua.getEngine().getName());
        cloakLog.setEngineVersion(ua.getEngineVersion());
        cloakLog.setIsMobile(ua.isMobile());
        if (ua.getPlatform() == Platform.Unknown) {
            return CheckStatus.FORBID_BY_SPIDER;
        }
        return null;
    }
}
