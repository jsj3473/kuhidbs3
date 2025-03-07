package com.example.kuhidbs.dto.Fund;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFundDTO {
    private String fundId; // 조합 고유번호
    private String fundName; // 조합명
    private String fundNameDetail; // 세부 조합명
    private String establishmentDate; // 설립일자 (YYYY-MM-DD)
    private Integer duration; // 존속기간 (년)
    private String durationStartDate; // 존속기간 시작일 (YYYY-MM-DD)
    private String durationEndDate; // 존속기간 종료일 (YYYY-MM-DD)
    private Integer investmentDuration; // 투자기간 (년)
    private String investmentStartDate; // 투자기간 시작일 (YYYY-MM-DD)
    private String investmentEndDate; // 투자기간 종료일 (YYYY-MM-DD)
    private String liquidationStatus; // 청산 여부
    private String liquidationDate; // 청산일자 (YYYY-MM-DD)
    private Long committedTotalPrice; // 약정 총액
    private Long unitPrice; // 1좌당 금액
    private String fundOrganizationType; // 투자기구 유형
    private String paymentType; // 납입방법
    private String leadFundManager; //대펀
    private String coreIvtManager; //핵운
    private String auditorName; //회계감사인명
    private String auditorDate; //회계감사인 변경일자
    private String trusteeCorporation; // 업무수탁법인
    private String administrationCorporation; // 사무수탁법인
    private BigDecimal targetReturnRate; // 기준 수익률
    private BigDecimal performanceFeeRate; // 성과 보수율
    private String managementFeeInvestmentPeriod; // 관리보수 (투자기간)
    private String managementFeeManagementPeriod; // 관리보수 (운영기간)
    private String agreementCriteria; // 약정기준여부
    private String incentiveCondition; // 인센티브 조건
    private String priorLossGP; // 우선손실충당 GP
    private String priorLossLP; // 우선손실충당 LP

    // 🔥 의무 투자
    private Long mandatoryTargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String mandatoryCriteria; //기준대상
    private Double mandatoryCriteriaRatio; //기준대상에 대한 비율

    // 🔥 주목적 투자 1
    private Long mainInvest1TargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String mainInvest1Criteria;
    private Double mainInvest1CriteriaRatio;

    // 🔥 주목적 투자 2
    private Long mainInvest2TargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String mainInvest2Criteria;
    private Double mainInvest2CriteriaRatio;

    // 🔥 특수목적 투자 1
    private Long specialInvest1TargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String specialInvest1Criteria;
    private Double specialInvest1CriteriaRatio;

    // 🔥 특수목적 투자 2
    private Long specialInvest2TargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String specialInvest2Criteria;
    private Double specialInvest2CriteriaRatio;

    // 🔥 특수목적 투자 3
    private Long specialInvest3TargetAmount; //기준대상이 기타일시 금액을 입력받아야함
    private String specialInvest3Criteria;
    private Double specialInvest3CriteriaRatio;
}
