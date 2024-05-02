package com.haishi.admin.shortlink.controller;

import com.haishi.admin.shortlink.service.ShortLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "shortLinkEngine", description = "Short Link Engine Controller")
@Controller
@RequestMapping("l")
@AllArgsConstructor
public class ShortLinkEngineController {

    private final ShortLinkService shortLinkService;

    @Operation(summary = "短链接口 ")
    @GetMapping("/{key}")
    public Object redirect(@PathVariable String key, Boolean preview, HttpServletRequest request) {
        return shortLinkService.access(key, request, preview);
    }
}
