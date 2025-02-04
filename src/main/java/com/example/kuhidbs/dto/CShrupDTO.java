package com.example.kuhidbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CShrupDTO {
    private Long investmentId; // 투자 고유 번호 (INV_ID)
    private String shareUpdateDate; // 변동 일자
    private Long shareUnitValue; // 주당 가치
    private String shareUpdateType; // 감액/복원
    private String shareUpdateReason; // 사유
    private String shareUpdateAction; // 조치 사항
}
