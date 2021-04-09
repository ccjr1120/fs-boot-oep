package com.boot.oep.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * @author ccjr
 * @date 2021/4/9 9:54 下午
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    @SneakyThrows
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        String path = courseFile + File.separator + "img" + File.separator;
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + path);
    }


}
