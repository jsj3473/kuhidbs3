package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.user.CreateUserDTO;
import com.example.kuhidbs.dto.user.UpdatePasswordDTO;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO createUserDTO) {
        // DTO를 엔티티로 변환
        User user = createUserDTO.toEntity();
        // 엔티티를 서비스로 전달
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
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
        return ResponseEntity.ok("Password for user with ID " + id + " has been reset to '1111'.");
    }

    //비밀번호 변경
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        String result = userService.updatePassword(
                updatePasswordDTO.getId(),
                updatePasswordDTO.getCurrentPassword(),
                updatePasswordDTO.getNewPassword()
        );
        return ResponseEntity.ok(result);
    }
}
