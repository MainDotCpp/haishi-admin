package com.haishi.admin.common.utils;

import cn.hutool.core.util.URLUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
@Component
public class WebShotUtils {
    private final static String API_URL = "https://s0.wp.com/mshots/v1/";

    public String getWebShotUrl(String url, int width, int height) {
        return API_URL + URLUtil.encodeAll(url) + "?w=" + width + "&h=" + height;
    }

    public String getWebShotUrl(String url) {
        return getWebShotUrl(url, 1440, 768);
    }

    @SneakyThrows
    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void downloadWebShot(String webUrl, String path, int width, int height) {
        URL url = new URL(getWebShotUrl(webUrl, width, height));

        log.info("下载网页截图 {}", url);
        // 添加请求头UA，否则会被拒绝
        RestTemplate restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");
        var entity = new HttpEntity<Object>(null, headers);
        byte[] bytes = restTemplate.postForObject(url.toURI(), entity, byte[].class);
        if (bytes == null) {
            throw new RuntimeException("下载网页截图失败");
        }
        // bytes 转为 BufferedImage
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        ImageIO.write(image, "jpg", new File(path));
    }
}


