package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.FangYuRequestDTO;
import com.haishi.admin.cloak.dto.FangYuResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface FangYuHttpClient {

    @PostExchange("/check/1451/{key}")
    FangYuResponseDTO check(@PathVariable("key") String key, @RequestBody FangYuRequestDTO body);
}
