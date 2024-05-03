package com.haishi.admin.shortlink.controller;

import cn.hutool.log.Log;
import com.haishi.admin.shortlink.service.ShortLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "shortLinkEngine", description = "Short Link Engine Controller")
@Controller
@AllArgsConstructor
public class ShortLinkEngineController {

    private final ShortLinkService shortLinkService;


    @GetMapping("/l")
    public Object base() {
        log.info("访问根路径");
        return "404";
    }

    @Operation(summary = "短链接口 ")
    @GetMapping("/l/{key}")
    public Object redirect(@PathVariable String key, Boolean preview, HttpServletRequest request) {
        return shortLinkService.access(key, request, preview);
    }
}
