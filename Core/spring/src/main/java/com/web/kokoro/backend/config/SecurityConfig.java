package com.web.kokoro.backend.config;
import com.web.kokoro.backend.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // 使用JWT，不需要csfr
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register","/login","/deepseek/chat/completions","/spark/chat/completions","test","/hachimi/authorize/**").permitAll()
                        .requestMatchers("/no").authenticated() // 需要认证
                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // 允许所有 OPTIONS 请求
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)// 禁用HTTP Basic认证
                .csrf(AbstractHttpConfigurer::disable); // 禁用CSRF保护（API通常不需要）
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}