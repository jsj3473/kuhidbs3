package com.example.kuhidbs.config;

import com.example.kuhidbs.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(sessionInterceptor)
//                .addPathPatterns("/**") // 모든 경로에 적용
//                .excludePathPatterns(
//                        "/login",          // 로그인 경로 제외
//                        "/api/users/login", // 로그인 폼 제출 요청 제외
//                        "/error",          // 에러 페이지 제외
//                        "/static/**",      // 정적 리소스 제외
//                        "/js/**",          // JS 파일 제외
//                        "/css/**",         // CSS 파일 제외
//                        "/images/**",      // 이미지 파일 제외
//                        "/webjars/**",     // WebJars 리소스 제외
//                );
//    }
}
