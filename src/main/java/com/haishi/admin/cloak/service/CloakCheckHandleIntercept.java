package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.CloakCheckContext;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CloakCheckHandleIntercept {

    abstract String checkName();

    abstract CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig);

    protected boolean isEnable(CloakLog cloakLog, CloakConfig cloakConfig) {
        return true;
    }

    CloakCheckContext execute(CloakCheckContext context) {
        CloakLog cloakLog = context.getCloakLog();
        CloakConfig cloakConfig = context.getCloakConfig();
        if (!isEnable(cloakLog, cloakConfig)) {
            log.info("[CLOAK] O 未开启" + checkName() + "模块");
            return context;
        }
        if (cloakLog.getStatus() != CheckStatus.PERMIT) {
            log.info("[CLOAK] - 前置检测未通过，跳过" + checkName());
            return context;
        }
        CheckStatus status = check(cloakLog, cloakConfig);
        if (status != null) {
            log.info("[CLOAK] X " + checkName() + "检测结果: " + status.getDesc());
            cloakLog.setStatus(status);
        } else {
            log.info("[CLOAK] O " + checkName() + "检测结果: 通过");
        }
        return context;
    }


}
