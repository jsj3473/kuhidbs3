package com.example.kuhidbs.dto.user;

import com.example.kuhidbs.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private String id;          // 사번
    private String currentPassword; // 현재 비밀번호
    private String newPassword;     // 새 비밀번호
}


