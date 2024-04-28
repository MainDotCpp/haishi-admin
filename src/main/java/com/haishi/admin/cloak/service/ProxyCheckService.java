package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProxyCheckService extends CloakCheckHandleIntercept {
    @Override
    String checkName() {
        return "代理检测";
    }

    @Override
    public CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig) {
        if (!cloakLog.getIsProxy()) {
            return null;
        }
        return CheckStatus.FORBID_BY_PROXY;
    }
}
