package com.example.kuhidbs.dto.company.kuhINV;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RIvtDTO {
    private Long investmentId; // 투자 ID
    private String investmentDate; // 투자 일자
    private String investmentProduct; // 투자 상품
    private String fundId; // 투자 재원
    private Long investmentSumPrice; // 투자 금액
    private Long investmentUnitPrice; // 투자 단가
    private Long shareCount; // 주식 수량
    private BigDecimal equityRate; // 지분율
    private Long totalShares; // 발행 주식 수
    private Long investmentValue; // 투자 밸류
    private String investmentStep; // 투자 단계
    private String tangibleInvestment; // 현물 투자
    private String investmentEmployee; // 투자 담당자
    private String managementFeeTarget; //관리보수대상 여부
    private String evaluationMethod; //평가방법
}



