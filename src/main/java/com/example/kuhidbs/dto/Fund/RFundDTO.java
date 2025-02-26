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
    private String currentStaff; //운용인력 전원  ex) 송승용(대펀),홍승표 (반기보고서 참조)
    private String auditorName; //회계감사법인
    private String trusteeCorporation; // 업무수탁법인
    private String administrationCorporation; // 사무수탁법인
    private BigDecimal targetReturnRate; // 기준 수익률
    private BigDecimal performanceFeeRate; // 성과 보수율
    private BigDecimal managementFeeInvestmentPeriod; // 관리보수 (투자기간)
    private BigDecimal managementFeeManagementPeriod; // 관리보수 (운영기간)
    private String incentiveCondition; // 인센티브 조건
    private String priorLossGP; // 우선손실충당 GP
    private String priorLossLP; // 우선손실충당 LP

    private String mandatoryInvestment1; // 의무투자
    private String mandatoryInvestment2;
    private String primaryInvestment1; // 주목적투자
    private String primaryInvestment2; // 주목적투자
    private String specialPurposeInvestment1; // 특수목적투자
    private String specialPurposeInvestment2; // 특수목적투자
    private String specialPurposeInvestment3; // 특수목적투자
    private String specialPurposeInvestment4; // 특수목적투자기타


    private List<RFundMemDTO> fundMems; //조합원 명부들
}
