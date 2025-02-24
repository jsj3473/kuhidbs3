package com.example.kuhidbs.dto.Fund;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RIASDTO {

    // 투자 기업 (회사명)
    private String investmentCompany;

    // 최초투자일 (YYYY-MM-DD)
    private String investmentDate;

    // 투자 방식
    private String investmentProduct;

    // 관리보수 대상 여부 (true: 대상, false: 비대상)
    private String managementFeeTarget;

    // 투자 원금
    private Long investmentAmount;

    // 감액
    private Long reductionAmount;

    // 회수 원금
    private Long recoveredPrincipal;

    // 회수 수익
    private Long recoveredProfit;

    // 투자 잔액
    private Long investmentBalance;

    // 잔여 자산 평가금액
    private Long remainingAssetValuation;

    // 멀티플
    private Double multiple;

    // 평가 방법
    private String evaluationMethod;
}
