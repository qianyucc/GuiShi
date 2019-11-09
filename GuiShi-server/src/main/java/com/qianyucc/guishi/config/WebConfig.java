package com.qianyucc.guishi.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author lijing
 * @date 2019-10-11 16:44
 * @description 解决跨域问题
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                .allowedHeaders("*")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
