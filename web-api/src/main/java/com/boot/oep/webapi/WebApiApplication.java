package com.boot.oep.webapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author ccjr
 * @date 2021/3/17 6:49 下午
 */
@SpringBootApplication(scanBasePackages = "com.boot.oep")
@MapperScan("com.boot.oep.mapper")
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
