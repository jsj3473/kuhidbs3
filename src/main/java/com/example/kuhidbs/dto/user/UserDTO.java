package com.example.kuhidbs.dto.user;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id; // 사번 (고유값)
    private String name; // 이름
    private String role; // 권한 (e.g., admin, user 등)
    private String registeredBy; // 등록자 사번

}
