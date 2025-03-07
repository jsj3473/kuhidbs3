package com.example.kuhidbs.dto.company.액분;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CStockSplitDto {
    private String companyId;
    private String splitDate;
    private Long preSplitUnitPrice;
    private Long postSplitUnitPrice;
    private Double splitRatio;
}
