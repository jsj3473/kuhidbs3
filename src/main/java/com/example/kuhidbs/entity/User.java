package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id; // 사번 (고유값, auto increment 아님)

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "password", nullable = false)
    private String password; // 비밀번호 (암호화된 값)

    @Column(name = "registered_by")
    private String registeredBy; // 등록자 사번

    @Column(name = "role", nullable = false, length = 100)
    private String role; // 권한 (e.g., admin, user 등)

    @Column(name = "is_employed", nullable = false)
    private boolean isEmployed = true; // 재직 여부, 기본값 true
}
