package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dao.CloakConfigRepository;
import com.haishi.admin.cloak.dao.CloakLogRepository;
import com.haishi.admin.cloak.dto.CloakCheckContext;
import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.dto.IpLocationDTO;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.cloak.mapper.CloakLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloakServiceImpl implements CloakService {
    private final CloakLogMapper cloakLogMapper;
    private final CloakConfigRepository cloakConfigRepository;
    private final CloakLogRepository cloakLogRepository;
    private final IpService ipService;
    private final CloakCheckChainService cloakCheckChainService;

    @Override
    public CloakCheckResult check(String key, CloakCheckDTO dto) {
        log.info("[CLOAK] 请求参数 key:{}", key);
        CloakLog cloakLog = cloakLogMapper.toEntity(dto);

        // 根据请求参数查询配置
        CloakConfig cloakConfig = cloakConfigRepository.findById(UUID.fromString(key)).orElseThrow();

        cloakLog.setConfigId(cloakConfig.getId().toString());

        // 检测 IP 地址
        IpLocationDTO locationDTO = ipService.request(cloakLog.getIp());
        log.info("[CLOAK] ip2location 接口返回结果:{}", locationDTO);
        CloakLogMapper.INSTANCE.updateCloakLogByLocationResult(cloakLog, locationDTO);
        cloakLog.setAccessTime(System.currentTimeMillis());
        cloakLog.setStatus(CheckStatus.PERMIT);

        // 解析 userAgent

        // 执行检测链
        CloakCheckContext context = new CloakCheckContext();
        context.setCloakLog(cloakLog);
        context.setCloakConfig(cloakConfig);
        cloakCheckChainService.execute(context);
        cloakLog.setRemark(cloakLog.getStatus().getDesc());
        cloakLogRepository.save(cloakLog);
        return new CloakCheckResult(cloakLog.getStatus().getCode() == 1, cloakLog.getStatus());
    }

    private static CloakLog parseRequest(HttpServletRequest request) {
        // 解析客户端参数
        CloakLog cloakLog = new CloakLog();
        cloakLog.setIp(Optional.of(request.getHeader("X-Real-IP")).orElse(Optional.of(request.getHeader("X-Forwarded-For")).orElse(request.getRemoteAddr())));
        cloakLog.setUserAgent(Optional.of(request.getHeader("User-Agent")).orElse(""));
        cloakLog.setLanguage(Optional.of(request.getHeader("Accept-Language")).orElse(""));
        cloakLog.setReferer(Optional.of(request.getHeader("Referer")).orElse(""));
        return cloakLog;
    }
}
