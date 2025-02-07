package com.example.kuhidbs.dto.company.동반;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RComDTO {
    private String companyId; // 회사고유번호
    private String investmentCompanyName; // 투자 기관 (COM_CMP_NM)
    private String investmentStartDate; // 투자 일자 (COM_STRT_DT)
    private Long investmentUnitPrice; // 투자 단가 (COM_UNIT_PRICR)
    private Long investmentSumPrice; // 투자 금액 (COM_SUM_PRICE)
    private String investmentProduct; // 투자 상품 (COM_PRDT)
    private BigDecimal equityRate; // 지분율 (COM_EQT_RATE)
    private String comment; // 비고 (COM_CMT)
    private String investmentStep; // 투자 단계 (INV_STEP)
    private Long shareCount; // 주식 수량 (SHR_CNT)
}
