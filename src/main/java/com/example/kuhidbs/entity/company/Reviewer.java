package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reviewer")
@Data
@NoArgsConstructor
public class Reviewer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동 생성
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 ID (외래키)


    @Column(name = "name", nullable = false)
    private String name; // 이름

    @Column(name = "share")
    private Double share; // 지분 (백분율)

    @Column(name = "type", nullable = false)
    private String type; // 발굴자 또는 심사자 또는 사후관리자)

    @Column(name = "appointment_date")
    private String appointmentDate; // 임명일자
}
