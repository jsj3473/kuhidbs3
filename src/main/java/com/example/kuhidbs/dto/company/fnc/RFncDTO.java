package com.example.kuhidbs.dto.company.fnc;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFncDTO {

    private Integer financialYear;
    private String financialHalf;
    private BigDecimal revenue;
    private BigDecimal operatingProfit;
    private BigDecimal netIncome;
    private BigDecimal totalAssets;
    private BigDecimal totalCapital;
    private BigDecimal capital;
    private Integer employeeCount;
    private BigDecimal totalDebt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
