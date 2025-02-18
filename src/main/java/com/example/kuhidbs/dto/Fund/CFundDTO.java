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
    private String auditorName; //회계감사인명
    private String auditorDate; //회계감사인 변경일자
    private String trusteeCorporation; // 업무수탁법인
    private String administrationCorporation; // 사무수탁법인
    private BigDecimal targetReturnRate; // 기준 수익률
    private BigDecimal performanceFeeRate; // 성과 보수율
    private BigDecimal managementFeeInvestmentPeriod; // 관리보수 (투자기간)
    private BigDecimal managementFeeManagementPeriod; // 관리보수 (운영기간)
    private String agreementCriteria; // 약정기준여부
    private String incentiveCondition; // 인센티브 조건
    private String priorLossGP; // 우선손실충당 GP
    private String priorLossLP; // 우선손실충당 LP
}
