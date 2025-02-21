package com.example.kuhidbs.dto.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDueStatusDTO {

    private String investmentCompany; // 투자기업
    private String investmentDate; // 투자일자
    private String investmentMethod; // 투자방식
    private BigInteger investmentAmount; // 투자금액

    private String dueDiligenceTarget; // 실사대상
    private String dueDiligenceCompleted; // 실시여부 (true: 실시 완료, false: 미실시)
    private String scheduledDate; // 예정일자

    private String issueExists; // 문제점 여부 (true: 문제 있음, false: 문제 없음)
    private String remarks; // 비고
}
