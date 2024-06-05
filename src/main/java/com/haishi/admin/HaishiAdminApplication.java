package com.haishi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class HaishiAdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(HaishiAdminApplication.class, args);
    }

}
