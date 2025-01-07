package com.example.kuhidbs.dto.user;

import com.example.kuhidbs.entity.User;

public class CreateUserDTO {
    private String id; // 사번
    private String name; // 이름
    private String password; // 비밀번호
    private String registeredBy; // 등록자 사번
    private String role; // 권한

    // 기본 생성자
    public CreateUserDTO() {}

    // 생성자
    public CreateUserDTO(String id, String name, String password, String registeredBy, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.registeredBy = registeredBy;
        this.role = role;
    }

    // Getter와 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='[PROTECTED]'" +
                ", registeredBy='" + registeredBy + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


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
