package com.example.kuhidbs.entity.company;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KUH_MNG_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "MNG_ID", nullable = false)
    private Long manageId; // 사후관리 고유번호 (PK)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company; // 회사 고유번호 (FK)

    @Column(name = "YEAR", nullable = false)
    private Integer year; // 년도

    @Column(name = "HALF_YEAR", nullable = false)
    private Integer halfYear; // 반기 (예: "상반기=01", "하반기=02")

    @Column(name = "EVAL_GRD", length = 50)
    private String evalGrade; // 평가등급 (String)

    @Column(name = "BIZ_PRGRS_1", length = 1000)
    private String businessProgress1;

    @Column(name = "BIZ_PRGRS_2", length = 1000)
    private String businessProgress2;

    @Column(name = "BIZ_PRGRS_3", length = 1000)
    private String businessProgress3;

    @Column(name = "BIZ_PRGRS_4", length = 1000)
    private String businessProgress4;

    @Column(name = "BIZ_PRGRS_5", length = 1000)
    private String businessProgress5;

    @Column(name = "MNG_STAT_1", length = 1000)
    private String managementStatus1;

    @Column(name = "MNG_STAT_2", length = 1000)
    private String managementStatus2;

    @Column(name = "EXIT_PLAN", length = 1000)
    private String exitPlan;

    @Column(name = "EXIT_ESTMT", length = 1000)
    private String exitEstimation;
}
