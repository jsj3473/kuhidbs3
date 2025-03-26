package com.example.kuhidbs.dto.Fund.dueDili;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
