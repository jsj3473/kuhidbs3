package com.example.kuhidbs.dto.company.invStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RStatusDTO {

    private String companyId; // 회사 고유 번호
    private String status; // 투자 상태
    private String additionalInfo; // 기타 정보

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
