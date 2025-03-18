package com.example.kuhidbs.dto.company.stocksplit;

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
