package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "KUH_TIPS_TBL") // 테이블 이름 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TIPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "TIPS_ID", nullable = false)
    private Long tipsId; // 팁스 현황 고유번호 (PK)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company; // 회사 고유번호 (FK)

    @Column(name = "TIPS_CMT", length = 1000)
    private String tipsComment; // 입력내용

    @Column(name = "TIPS_SLT_DT", length = 100)
    private String tipsSelectionDate; // 선정일자

    @Column(name = "TIPS_SLT_TP", length = 100)
    private String tipsSelectionType; // 선정종류

    @Column(name = "EXCU_STRT_DT", length = 100)
    private String executionStartDate; // 수행기간 시작일

    @Column(name = "EXCU_END_DT", length = 100)
    private String executionEndDate; // 수행기간 종료일

    @Column(name = "SEL_YN", length = 20)
    private String selectionYesNo; // 선정여부

    @Column(name = "TIPS_MNG_END_DT", length = 100)
    private String tipsManagementEndDate; // 사후기간 종료일

    @Column(name = "SUCCESS_YN", length = 20)
    private String successYesNo; // 성공여부

    @Column(name = "FOLLOW_TIPS", length = 20)
    private String followTips; // 사후성공여부

    @Column(name = "KIPS", length = 20)
    private String kips; // 성공정량지표
}
