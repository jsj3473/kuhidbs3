package com.example.kuhidbs.dto.company.kuh투자;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CIvtDTO {

    private String fundId; // 투자 재원
    private String companyId; // 회사 고유 번호
    private String investmentProduct; // 투자 상품
    private Long investmentSumPrice; // 투자 금액
    private Long shareCount; // 주식 수량
    private Long investmentUnitPrice; // 투자 단가
    private BigDecimal equityRate; // 지분율
    private Long investmentValue; // 투자 밸류
    private String tangibleInvestment; // 현물 투자
    private String investmentStep; // 투자 단계
    private String investmentDate; // 투자 일자
    private String investmentEmployee; // 투자 담당자
    private Long totalShares; // 발행 주식 수
    private String investmentState; // 투자 상태
    private String investmentMemo; // 기타 메모
    private String managementFeeTarget; //관리보수대상 여부
    private String evaluationMethod; //평가방법
}



