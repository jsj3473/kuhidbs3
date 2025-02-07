package com.example.kuhidbs.entity.Fund;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_FUND_TBL") // 테이블 이름을 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FUND_ID", nullable = false)
    private Long fundId; // 조합 고유번호 (PK)

    @Column(name = "FUND_NM", length = 200)
    private String fundName; // 조합명

    @Column(name = "EST_DATE", length = 10)
    private String establishmentDate; // 설립일자 (YYYY-MM-DD)

    @Column(name = "DUR_STRT_DT", length = 10)
    private String durationStartDate; // 존속기간 시작일 (YYYY-MM-DD)

    @Column(name = "DUR_END_DT", length = 10)
    private String durationEndDate; // 존속기간 종료일 (YYYY-MM-DD)

    @Column(name = "INV_STRT_DT", length = 10)
    private String investmentStartDate; // 투자기간 시작일 (YYYY-MM-DD)

    @Column(name = "INV_END_DT", length = 10)
    private String investmentEndDate; // 투자기간 종료일 (YYYY-MM-DD)

    @Column(name = "CMTD_TOT_PRICE")
    private Long committedTotalPrice; // 약정 총액

    @Column(name = "PAYMT_TP", length = 200)
    private String paymentType; // 납입방법

    @Column(name = "FUND_MNG", length = 20)
    private String fundManager; // 대표 펀드매니저

    @Column(name = "FUND_NM_DETAIL", length = 200)
    private String fundNameDetail; // 세부 조합명

    @Column(name = "FUND_ORG_TP", length = 200)
    private String fundOrganizationType; // 투자기구 형태

    @Column(name = "TIPS_TP", length = 200)
    private String tipsType; // 팁스 타입

    @Column(name = "FUN_MAIN_REQ", length = 200)
    private String fundMainRequirement; // 조합 주요 요건

    @Column(name = "TRGT_RTN_RATE", precision = 5, scale = 2)
    private BigDecimal targetReturnRate; // 기준 수익률

    @Column(name = "PFMC_FEE_RATE", precision = 5, scale = 2)
    private BigDecimal performanceFeeRate; // 성과 보수율

    @Column(name = "MNG_FEE_1", precision = 5, scale = 2)
    private BigDecimal managementFee1; // 관리 보수 1

    @Column(name = "MNG_FEE_2", precision = 5, scale = 2)
    private BigDecimal managementFee2; // 관리 보수 2

    @Column(name = "INCENTIVE_1", length = 200)
    private String incentive1; // 인센티브 1

    @Column(name = "INCENTIVE_2", length = 200)
    private String incentive2; // 인센티브 2

    @Column(name = "INCENTIVE_3", length = 200)
    private String incentive3; // 인센티브 3

    @Column(name = "INCENTIVE_4", length = 200)
    private String incentive4; // 인센티브 4

    @Column(name = "PRIOR_LOSS_1", length = 200)
    private String priorLoss1; // 우선 손실 충당 1

    @Column(name = "PRIOR_LOSS_2", length = 200)
    private String priorLoss2; // 우선 손실 충당 2

    @Column(name = "DUTY_RATE_1", length = 200)
    private String dutyRate1; // 의무 투자 비율 1

    @Column(name = "DUTY_RATE_2", length = 200)
    private String dutyRate2; // 의무 투자 비율 2

    @Column(name = "DUTY_RATE_3", length = 200)
    private String dutyRate3; // 의무 투자 비율 3

    @Column(name = "DUTY_RATE_4", length = 200)
    private String dutyRate4; // 의무 투자 비율 4

    @Column(name = "DUTY_RATE_5", length = 200)
    private String dutyRate5; // 의무 투자 비율 5

    @Column(name = "DUTY_RATE_6", length = 200)
    private String dutyRate6; // 의무 투자 비율 6

    @Column(name = "DUTY_RATE_7", length = 200)
    private String dutyRate7; // 의무 투자 비율 7

    @Column(name = "DUTY_RATE_8", length = 200)
    private String dutyRate8; // 의무 투자 비율 8
}
