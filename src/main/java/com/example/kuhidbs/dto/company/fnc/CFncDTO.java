package com.example.kuhidbs.dto.company.fnc;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFncDTO {

    private String companyId; // Company 객체 대신 ID만 포함
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
}
