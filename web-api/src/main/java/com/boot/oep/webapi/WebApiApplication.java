package com.boot.oep.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ccjr
 * @date 2021/3/17 6:49 下午
 */
@SpringBootApplication(scanBasePackages = "com.boot.oep")
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
