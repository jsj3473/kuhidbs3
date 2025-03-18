package com.example.kuhidbs.dto.company.shrup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RShrupDTO {
    private String shareUpdateDate; // 변동 일자
    private Long shareUnitValue; // 주당 가치
    private Long curUnitValue; // 현재 주당 가치
    private String shareUpdateType; // 감액/복원
    private String shareUpdateReason; // 사유
    private String shareUpdateAction; // 조치 사항
}
