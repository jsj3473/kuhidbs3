package com.example.kuhidbs.config;

import com.example.kuhidbs.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final HttpSession session; // HttpSession 주입

    @Override
    public Optional<String> getCurrentAuditor() {
        // 세션에서 사용자 정보 가져오기
        Object userObject = session.getAttribute("loggedInUser");

        if (userObject == null) {
            return Optional.empty(); // 로그인되지 않은 경우
        }

        // User 엔티티로 변환 후 사번 반환
        User user = (User) userObject;
        return Optional.of(user.getId()); // ID 또는 사번을 반환
    }
}
