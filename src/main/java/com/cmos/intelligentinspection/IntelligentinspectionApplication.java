package com.cmos.intelligentinspection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan({"com.cmos.intelligentinspection.admin.controller","com.cmos.intelligentinspection.common.controller"})
public class IntelligentinspectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntelligentinspectionApplication.class, args);
    }

}
