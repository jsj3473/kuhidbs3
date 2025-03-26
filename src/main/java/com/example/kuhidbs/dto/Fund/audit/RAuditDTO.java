package com.example.kuhidbs.dto.Fund.audit;

import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
