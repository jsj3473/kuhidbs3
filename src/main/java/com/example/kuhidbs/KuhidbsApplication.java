package com.example.kuhidbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl") // AuditorAware 등록
public class KuhidbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuhidbsApplication.class, args);
    }

}
