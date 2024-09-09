package com.haishi.admin.resource.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.resource.dto.GoddyDomainDTO;
import com.haishi.admin.resource.entity.DomainAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
public class GoddyClient {

    private final String BASE_URL = "https://api.godaddy.com/";
    private final DomainAccount domainAccount;


    public GoddyClient(DomainAccount domainAccount) {
        this.domainAccount = domainAccount;
    }

    private String getAuthHeader() {
        return this.domainAccount.getKey() + ":" + this.domainAccount.getSecret();
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "sso-key " + getAuthHeader());
            request.getHeaders().add("Content-Type", "application/json");
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    public GoddyDomainDTO[] getDomainList() {
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForObject(BASE_URL + "v1/domains", GoddyDomainDTO[].class);
    }

    public void configDns(String domain, String address) {
        log.info("configDns: domain={}, address={}", domain, address);
        RestTemplate restTemplate = getRestTemplate();
        HashMap<String, Object> mainRecord = new HashMap<>();
        mainRecord.put("data", address);
        mainRecord.put("name", "@");
        mainRecord.put("ttl", 600);
        mainRecord.put("type", "A");

        HashMap<String, Object> wwwRecord = new HashMap<>();
        wwwRecord.put("data", address);
        wwwRecord.put("name", "www");
        wwwRecord.put("ttl", 600);
        wwwRecord.put("type", "A");

        HashMap<String, Object> ns07Record = new HashMap<>();
        ns07Record.put("data", "ns07.domaincontrol.com");
        ns07Record.put("name", "@");
        ns07Record.put("ttl", 3600);
        ns07Record.put("type", "NS");

        HashMap<String, Object> ns08Record = new HashMap<>();
        ns08Record.put("data", "ns08.domaincontrol.com");
        ns08Record.put("name", "@");
        ns08Record.put("ttl", 3600);
        ns08Record.put("type", "NS");

        HashMap<String, Object>[] records = new HashMap[]{mainRecord, wwwRecord, ns07Record, ns08Record};
        restTemplate.put(BASE_URL + "v1/domains/" + domain + "/records", records);
    }
}
