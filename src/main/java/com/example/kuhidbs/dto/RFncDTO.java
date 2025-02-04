package com.example.kuhidbs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFncDTO {

    private Integer financialYear;
    private Integer financialHalf;
    private Integer revenue;
    private Integer operatingProfit;
    private Integer netIncome;
    private Integer totalAssets;
    private Integer totalCapital;
    private Integer capital;
    private Integer employeeCount;
    private Integer totalDebt;
}
