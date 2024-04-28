package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.IpLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface IpService {

    @GetExchange
    IpLocationDTO request(@RequestParam("ip") String ip);
}
