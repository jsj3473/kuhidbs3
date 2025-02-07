package com.example.kuhidbs.dto.Fund;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFundDTO {
    private String fundName; // 조합명
    private String fundNameDetail; // 세부 조합명
    private String establishmentDate; // 설립일자 (YYYY-MM-DD)
    private String durationStartDate; // 존속기간 시작일 (YYYY-MM-DD)
    private String durationEndDate; // 존속기간 종료일 (YYYY-MM-DD)
    private String investmentStartDate; // 투자기간 시작일 (YYYY-MM-DD)
    private String investmentEndDate; // 투자기간 종료일 (YYYY-MM-DD)
    private Long committedTotalPrice; // 약정 총액
    private String paymentType; // 납입방법
    private String fundManager; // 대표 펀드매니저
    private String fundOrganizationType; // 투자기구 형태
    private String tipsType; // 팁스 타입
    private String fundMainRequirement; // 조합 주요 요건
    private BigDecimal targetReturnRate; // 기준 수익률
    private BigDecimal performanceFeeRate; // 성과 보수율
    private BigDecimal managementFee1; // 관리 보수 1
    private BigDecimal managementFee2; // 관리 보수 2
    private String incentive1; // 인센티브 1
    private String incentive2; // 인센티브 2
    private String incentive3; // 인센티브 3
    private String incentive4; // 인센티브 4
    private String priorLoss1; // 우선 손실 충당 1
    private String priorLoss2; // 우선 손실 충당 2
    private String dutyRate1; // 의무 투자 비율 1
    private String dutyRate2; // 의무 투자 비율 2
    private String dutyRate3; // 의무 투자 비율 3
    private String dutyRate4; // 의무 투자 비율 4
    private String dutyRate5; // 의무 투자 비율 5
    private String dutyRate6; // 의무 투자 비율 6
    private String dutyRate7; // 의무 투자 비율 7
    private String dutyRate8; // 의무 투자 비율 8
}
