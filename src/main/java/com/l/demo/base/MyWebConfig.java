package com.l.demo.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by luocheng on 2019/3/13.
 */

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      //  registry.addResourceHandler("/image/**").addResourceLocations("file:F:/UcapProject/demo/images/");
      //  registry.addResourceHandler("/video/**").addResourceLocations("file:F:/UcapProject/demo/videos/");

        registry.addResourceHandler("/image/**").addResourceLocations("file:/home/demoApp/images/");
        registry.addResourceHandler("/video/**").addResourceLocations("file:/home/demoApp/videos/");

    }
}