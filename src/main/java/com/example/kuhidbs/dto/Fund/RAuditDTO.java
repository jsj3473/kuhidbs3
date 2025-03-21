package com.example.kuhidbs.dto.Fund;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RAuditDTO {
    private Long auditId;
    private String fncYear; //년도
    private String changeDate; // 변경일자 (YYYY-MM-DD)
    private String auditorName; // 회계감사인명
    private String changeReason; // 변경사유
}
