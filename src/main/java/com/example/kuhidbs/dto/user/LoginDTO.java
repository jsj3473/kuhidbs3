package com.example.kuhidbs.dto.user;

import com.example.kuhidbs.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String id; // 사번
    private String password;

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return user;
    }
}
