package com.haishi.admin.resource.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class LandingServiceTest {

    @Resource
    private LandingService landingService;

    @Test
    void reformIndex() {
        landingService.reformIndex("");
    }
}