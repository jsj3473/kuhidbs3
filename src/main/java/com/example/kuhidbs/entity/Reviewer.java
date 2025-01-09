package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reviewer")
@Data
@NoArgsConstructor
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(name = "company_id", nullable = false)
    private Long companyId; // 회사 ID (외래 키)

    @ElementCollection
    @CollectionTable(name = "company_explorers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "explorer_name")
    private List<String> explorers; // 발굴자 (여러 명 가능)

    @ElementCollection
    @CollectionTable(name = "company_reviewers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "reviewer_name")
    private List<String> reviewers; // 심사자 (여러 명 가능)

    @Column(name = "explorer_share", nullable = false)
    private Double explorerShare; // 발굴자 지분 (백분율)

    @Column(name = "reviewer_share", nullable = false)
    private Double reviewerShare; // 심사자 지분 (백분율)

    @Column(name = "post_manager", length = 50, nullable = false)
    private String postManager; // 사후관리자

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate; // 등록일자
}
