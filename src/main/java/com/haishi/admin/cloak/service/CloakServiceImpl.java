package com.haishi.admin.cloak.service;

import cn.hutool.core.lang.func.Func;
import com.haishi.admin.cloak.dao.CloakConfigRepository;
import com.haishi.admin.cloak.dao.CloakLogRepository;
import com.haishi.admin.cloak.dto.CloakCheckContext;
import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.dto.IpLocationDTO;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.mapper.CloakLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

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
    public CloakCheckResult check(String key, CloakCheckDTO dto, Function<CloakLog, CloakLog> extraLog) {
        log.info("[CLOAK] 请求参数 key:{}", key);
        CloakLog cloakLog = cloakLogMapper.toEntity(dto);

        // 根据请求参数查询配置
        CloakConfig cloakConfig = cloakConfigRepository.findById(UUID.fromString(key)).orElseThrow();

        cloakLog.setConfigId(cloakConfig.getId().toString());

        // 检测 IP 地址
        IpLocationDTO locationDTO = new IpLocationDTO();
        locationDTO.setIp(cloakLog.getIp());
        locationDTO.setCountryCode("");
        locationDTO.setIsProxy(false);
        try {
            locationDTO = ipService.request(cloakLog.getIp(), null);
        } catch (Exception e) {
            log.error("[CLOAK] ip2location 接口请求异常", e);
        }

        log.info("[CLOAK] ip2location 接口返回结果:{}", locationDTO);
        CloakLogMapper.INSTANCE.updateCloakLogByLocationResult(cloakLog, locationDTO);
        cloakLog.setAccessTime(System.currentTimeMillis());
        cloakLog.setStatus(CheckStatus.PERMIT);
        extraLog.apply(cloakLog);

        // 解析 userAgent

        // 执行检测链
        CloakCheckContext context = new CloakCheckContext();
        context.setCloakLog(cloakLog);
        context.setCloakConfig(cloakConfig);
        cloakCheckChainService.execute(context);
        cloakLogRepository.save(cloakLog);
        return new CloakCheckResult(cloakLog.getStatus().getCode() == 1, cloakLog.getStatus());
    }

    @Override
    public CloakCheckResult check(String key, HttpServletRequest request, Function<CloakLog, CloakLog> extraLog) {
        CloakLog cloakLog = parseRequest(request);
        return check(key, cloakLogMapper.toDto(cloakLog), extraLog);
    }

    private static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static CloakLog parseRequest(HttpServletRequest request) {
        // 解析客户端参数
        CloakLog cloakLog = new CloakLog();
        cloakLog.setIp(getRealIp(request));
        cloakLog.setUserAgent(request.getHeaders("User-Agent").nextElement());
        cloakLog.setLanguage(request.getHeader("Accept-Language"));
        cloakLog.setReferer(request.getHeader("Referer"));
        // 获取请求的地址
        cloakLog.setAccessUrl(request.getRequestURL().toString());
        return cloakLog;
    }
}
