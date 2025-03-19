package com.example.kuhidbs.dto.Fund;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFundDTO {
    private String fundId; // 조합 고유번호
    private String liquidationStatus; // 청산 여부
    private String liquidationDate; // 청산일자 (YYYY-MM-DD)
    private String fundName; // 조합명
    private String fundNameDetail; // 세부 조합명
    private String establishmentDate; // 설립일자 (YYYY-MM-DD)
    private Integer duration; // 존속기간
    private String durationStartDate; // 존속기간 시작일 (YYYY-MM-DD)
    private String durationEndDate; // 존속기간 종료일 (YYYY-MM-DD)
    private Integer investmentDuration; // 투자기간
    private String investmentStartDate; // 투자기간 시작일 (YYYY-MM-DD)
    private String investmentEndDate; // 투자기간 종료일 (YYYY-MM-DD)
    private Long committedTotalPrice; // 약정 총액
    private Long unitPrice; // 1좌당 금액
    private String fundOrganizationType; // 투자기구 유형
    private String paymentType; // 납입방법
    private String leadFundManager; //대펀
    private String coreIvtManager; //핵운
    private String auditorName; //회계감사인명
    private String trusteeCorporation; // 업무수탁법인
    private String administrationCorporation; // 사무수탁법인
    private BigDecimal targetReturnRate; // 기준 수익률
    private BigDecimal performanceFeeRate; // 성과 보수율
    private String managementFeeInvestmentPeriod; // 관리보수 (투자기간)
    private String managementFeeManagementPeriod; // 관리보수 (운영기간)
    private String incentiveCondition; // 인센티브 조건
    private String priorLossGP; // 우선손실충당 GP
    private String priorLossLP; // 우선손실충당 LP

    private String mandatoryPurpose; // 의무투자
    private String mainInvest1Purpose; // 주목적투자
    private String mainInvest2Purpose; // 주목적투자
    private String specialInvest1Purpose; // 특수목적투자
    private String specialInvest2Purpose; // 특수목적투자
    private String specialInvest3Purpose; // 특수목적투자


    private List<RFundMemDTO> fundMems; //조합원 명부들
    private RFundAchievementDTO fundAchievement;
}
