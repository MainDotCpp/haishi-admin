package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.CloakCheckContext;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloakCheckChainService {
    private final RegionCheckService regionCheckService;
    private final ProxyCheckService proxyCheckService;
    private final UaCheckService uaCheckService;
    private final FangYuCloakService fangYuCloakService;
    private final BlacklistIpCheckService blacklistIpCheckService;

    private List<CloakCheckHandleIntercept> handleList = new ArrayList<CloakCheckHandleIntercept>();

    @PostConstruct
    public void init() {
        handleList.add(uaCheckService);
        handleList.add(regionCheckService);
        handleList.add(proxyCheckService);
        handleList.add(fangYuCloakService);
        handleList.add(blacklistIpCheckService);
    }

    public CloakCheckContext execute(CloakCheckContext context) {
        for (CloakCheckHandleIntercept handle : handleList) {
            context = handle.execute(context);
        }
        return context;
    }
}
