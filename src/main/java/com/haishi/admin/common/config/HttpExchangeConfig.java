package com.haishi.admin.common.config;

import com.haishi.admin.cloak.service.FangYuHttpClient;
import com.haishi.admin.cloak.service.IpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpExchangeConfig {
    @Bean
    public IpService ipService() {
        WebClient client = WebClient.builder().baseUrl("https://api.ip2location.io").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(IpService.class);
    }

    @Bean
    public FangYuHttpClient fangYuHttpClient() {
        WebClient client = WebClient.builder().baseUrl("https://api-visitor.fangyu.io")
                .defaultHeader("User-Agent", "Mozilla/5.0")
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(FangYuHttpClient.class);
    }
}
