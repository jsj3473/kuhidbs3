package com.example.kuhidbs.dto.user;

import com.example.kuhidbs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
public class CreateUserDTO {
    private String id; // 사번
    private String name; // 이름
    private String password; // 비밀번호
    private String registeredBy; // 등록자 사번
    private String role; // 권한



    // DTO -> Entity 변환 메서드
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setPassword(this.password); // 비밀번호 암호화는 서비스에서 처리
        user.setRegisteredBy(this.registeredBy);
        user.setRole(this.role);
        return user;
    }
}
