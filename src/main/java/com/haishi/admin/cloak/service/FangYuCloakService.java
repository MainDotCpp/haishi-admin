package com.haishi.admin.cloak.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.cloak.dto.FangYuRequestDTO;
import com.haishi.admin.cloak.dto.FangYuResponseDTO;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.cloak.mapper.CloakLogMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FangYuCloakService extends CloakCheckHandleIntercept {

    private final FangYuHttpClient fangYuHttpClient;


    @Override
    String checkName() {
        return "第三方检测(FANGYU)";
    }


    @Override
    protected boolean isEnable(CloakLog cloakLog, CloakConfig cloakConfig) {
        return cloakConfig.getUseCloakProvider();
    }

    @SneakyThrows
    @Override
    public CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig) {
        FangYuRequestDTO fangYuRequestDTO = CloakLogMapper.INSTANCE.logToFangYuRequestDTO(cloakLog);
        fangYuRequestDTO.setSign(cloakConfig.getCloakProviderApiSecret());
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(fangYuRequestDTO);
        log.info("[CLOAK] 请求第三方 CLOAK 服务: {}", s);
        FangYuResponseDTO responseDTO = fangYuHttpClient.check(cloakConfig.getCloakProviderApiUrl(), fangYuRequestDTO);
        log.info("[CLOAK] 第三方 CLOAK 服务返回: {}", responseDTO);
        cloakLog.setThirdCloakResponse(om.writeValueAsString(responseDTO));
        if (!responseDTO.getData().getStatus()) {
            cloakLog.setRemark(responseDTO.getData().getMessage());
            return CheckStatus.FORBID_BY_THIRD_CLOAK;
        }
        return null;
    }
}
