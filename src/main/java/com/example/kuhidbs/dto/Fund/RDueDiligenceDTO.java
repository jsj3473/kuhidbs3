package com.example.kuhidbs.dto.Fund;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RDueDiligenceDTO {

    private String companyNm; //투자기업
    private String investmentDate; // 투자 일자
    private String investmentProduct; // 투자 상품
    private Long investmentSumPrice; // 투자 금액
    private String target; //실사대상
    private String status; //실시여부
    private String inspectionDate;     //실사일자
    private String issues; //문제점발견
}
