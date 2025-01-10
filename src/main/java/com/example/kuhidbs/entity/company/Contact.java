package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Integer contactId; // Contact 고유 ID

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 ID (외래키)

    @Column(name = "name", nullable = false, length = 50)
    private String name; // 이름

    @Column(name = "position", length = 50)
    private String position; // 직책

    @Column(name = "email", length = 100)
    private String email; // 이메일 주소

    @Column(name = "phone_number", length = 15)
    private String phoneNumber; // 전화번호
}
