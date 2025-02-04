package com.example.kuhidbs.controller;

import ch.qos.logback.core.model.Model;
import com.example.kuhidbs.dto.user.CreateUserDTO;
import com.example.kuhidbs.dto.user.LoginDTO;
import com.example.kuhidbs.dto.user.UpdatePasswordDTO;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String  createUser(@ModelAttribute CreateUserDTO createUserDTO) {
        System.out.println("DTO 값: " + createUserDTO);
        // DTO를 엔티티로 변환
        User user = createUserDTO.toEntity();
        // 엔티티를 서비스로 전달
        return userService.createUser(user);
    }

    //퇴직 처리
    @PutMapping("/{id}/retire")
    public ResponseEntity<String> retireUser(@PathVariable String id) {
        userService.retireUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been marked as retired.");
    }

    // 비밀번호 초기화
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String id) {
        userService.resetPassword(id);
        return ResponseEntity.ok("Password for user with ID " + id + " has been reset to '1'.");
    }

    //비밀번호 변경
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@ModelAttribute UpdatePasswordDTO updatePasswordDTO) {
        String result = userService.updatePassword(
                updatePasswordDTO.getId(),
                updatePasswordDTO.getCurrentPassword(),
                updatePasswordDTO.getNewPassword()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public void login(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response) {
        try {
            System.out.println("Session set");
            userService.login(loginDTO);
            // 로그인 성공 시 main으로 리다이렉트
            response.sendRedirect("/main");
        } catch (IllegalArgumentException | IOException e) {
            // 로그인 실패 시 로그인 페이지로 리다이렉트
            try {
                response.sendRedirect("/login?error=true");
            } catch (Exception ex) {
                throw new RuntimeException("Redirect failed", ex);
            }
        }
    }

    @GetMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        // 세션 무효화
        session.invalidate();

        // 로그아웃 후 리다이렉트
        response.sendRedirect("/login");
    }
}
