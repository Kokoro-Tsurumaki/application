package com.web.kokoro.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // 允许所有路径
            .allowedOrigins("*") // 允许的前端地址
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
            .allowedHeaders("*") // 允许所有头
            .allowCredentials(false) // 允许发送Cookie
            .maxAge(3600) // 预检请求的有效期
    }
}