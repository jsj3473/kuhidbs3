package com.example.kuhidbs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CBonusDTO {

    private Long investmentId; // 투자 고유 번호 (INV_ID)
    private Long changedShareCount; // 변경 주식 수
    private Long unitPrice; // 투자 단가
}
