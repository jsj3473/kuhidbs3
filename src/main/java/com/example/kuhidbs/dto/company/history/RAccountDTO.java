package com.example.kuhidbs.dto.company.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RAccountDTO {
    private Long investmentId; // 투자 고유 번호 (INV_ID)
    private String accountDate; //일자
    private Long unitPrice; // 투자 단가
    private Long heldShareCount; // 보유 주식 수
    private Long totalPrincipal; // 투자 원금
    private String functionType; // 실행 함수
    private BigDecimal kuhEquityRate; // KUH 지분율
    private Long curUnitPrice; // 현재 주당 단가
    private Long postValue; // 현재 시가총액
    private Long totalShareCount; // 발행총 주식수
}
