package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "KUH_FUND_INFO") // 테이블 이름을 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fund extends BaseEntity {

    @Id
    @Column(name = "FUND_ID", length = 20, nullable = false)
    private String fundId; // 조합 고유번호 (PK)

    @Column(name = "FUND_NM", length = 200)
    private String fundName; // 조합명

    @Column(name = "EST_DATE", length = 100)
    private String establishmentDate; // 설립일자 (YYYY-MM-DD)

    @Column(name = "DUR_STRT_DT", length = 100)
    private String durationStartDate; // 존속기간 시작일 (YYYY-MM-DD)

    @Column(name = "DUR_END_DT", length = 100)
    private String durationEndDate; // 존속기간 종료일 (YYYY-MM-DD)

    @Column(name = "INV_STRT_DT", length = 100)
    private String investmentStartDate; // 투자기간 시작일 (YYYY-MM-DD)

    @Column(name = "INV_END_DT", length = 100)
    private String investmentEndDate; // 투자기간 종료일 (YYYY-MM-DD)

    @Column(name = "CMTD_TOT_PRICE")
    private Long committedTotalPrice; // 약정 총액

    @Column(name = "PAYMT_TP", length = 200)
    private String paymentType; // 납입방법

    @Column(name = "FUND_NM_DETAIL", length = 200)
    private String fundNameDetail; // 세부 조합명

    @Column(name = "FUND_ORG_TP", length = 200)
    private String fundOrganizationType; // 투자기구 형태

    @Column(name = "FUN_MAIN_REQ", length = 200)
    private String fundMainRequirement; // 조합 주요 요건

    @Column(name = "TRGT_RTN_RATE", precision = 5, scale = 2)
    private BigDecimal targetReturnRate; // 기준 수익률

    @Column(name = "PFMC_FEE_RATE", precision = 5, scale = 2)
    private BigDecimal performanceFeeRate; // 성과 보수율

    @Column(name = "MNG_FEE_INV", length = 200)
    private String managementFeeInvestmentPeriod; // 관리보수 (투자기간)

    @Column(name = "MNG_FEE_MNG", length = 200)
    private String managementFeeManagementPeriod; // 관리보수 (운영기간)

    @Column(name = "INCENTIVE", length = 200)
    private String incentiveCondition; // 인센티브 조건

    @Column(name = "UNIT_PRICE")
    private Long unitPrice; // 1좌당 금액

    @Column(name = "AGREEMENT_CRITERIA", length = 200)
    private String agreementCriteria; // 약정기준여부

    @Column(name = "PRIOR_LOSS_GP", length = 200)
    private String priorLossGP; // 우선손실충당 GP

    @Column(name = "PRIOR_LOSS_LP", length = 200)
    private String priorLossLP; // 우선손실충당 LP

    @Column(name = "DURATION")
    private Integer duration; // 존속기간

    @Column(name = "LIQUIDATION_STATUS", length = 200)
    private String liquidationStatus; // 청산 여부

    @Column(name = "LIQUIDATION_DATE", length = 100)
    private String liquidationDate; // 청산일자 (YYYY-MM-DD)

    @Column(name = "TRUSTEE_CORP", length = 200)
    private String trusteeCorporation; // 업무수탁법인

    @Column(name = "ADMIN_CORP", length = 200)
    private String administrationCorporation; // 사무수탁법인

    @Column(name = "INVESTMENT_DURATION")
    private Integer investmentDuration; // 투자기간

    @Column(name = "LEAD_FUND_MNG", length = 800)
    private String leadFundManager; // 대펀

    @Column(name = "CORE_IVT_MNG", length = 800)
    private String coreIvtManager; // 핵운

    @Column(name = "ALLOC_RATIO", precision = 5, scale = 2)
    private BigDecimal allocRatio; // 배분비율

    @Column(name = "IVT_RATIO", precision = 5, scale = 2)
    private BigDecimal ivtRatio; // 고려대 출자비율(약정비율)

    // ✅ 추가된 컬럼들
    @Column(name = "ALLOC_PRINCIPAL")
    private Integer allocPrincipal; // 배분원금

    @Column(name = "ALLOC_PROFIT")
    private Integer allocProfit; // 배분이익

    @Column(name = "ALLOC_TOTAL")
    private Integer allocTotal; // 배분총액

}