package com.example.kuhidbs.dto.company.followup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFolDTO {

    private String followupStartDate; // 투자 일자
    private String followupCompanyName; // 투자 기관
    private String followupProduct; // 투자 상품
    private Long followupSumPrice; // 투자 금액
    private Long followupShareCount; // 주식 수량
    private Long followupUnitPrice; // 투자 단가
    private Long followupInvestmentValue; // 투자 밸류

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
