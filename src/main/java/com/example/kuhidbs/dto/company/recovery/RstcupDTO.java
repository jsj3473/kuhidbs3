package com.example.kuhidbs.dto.company.recovery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RstcupDTO {
    private String companyId; // 회사 고유 번호
    private Long investmentId; // 투자 고유 번호 (INV_ID)
    private String recoveryDate; // 회수 일자 (STCUP_DT)
    private Long recoveryCount; // 매각 수량 (STCUP_CNT)
    private Long recoveryUnitPrice; // 매각 단가 (STCUP_UNIT_PRICE)
    private Long fundReturn; // 펀드 수익 (STCUP_FUND_RTN)
    private Long kuhReturn; // KUH 수익 (STCUP_KUH_RTN)

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
