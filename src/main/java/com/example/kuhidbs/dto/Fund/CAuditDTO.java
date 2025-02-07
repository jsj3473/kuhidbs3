package com.example.kuhidbs.dto.Fund;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CAuditDTO {

    private Long fundId; // 조합 고유번호 (FK)
    private String changeDate; // 변경일자 (YYYY-MM-DD)
    private String auditorName; // 회계감사인명
    private String changeReason; // 변경사유
}
