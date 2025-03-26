package com.example.kuhidbs.dto.Fund.dueDili;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CDueDiligenceDTO {
    private Long investmentId;
    private String target; //실사대상
    private String status; //실시여부
    private String inspectionDate;     //실사일자
    private String issues; //문제점발견
}
