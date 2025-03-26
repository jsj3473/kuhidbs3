package com.example.kuhidbs.dto.company.reviewer;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RRwrDTO {

    private String managerType;
    private String manager;
    private String changeReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
