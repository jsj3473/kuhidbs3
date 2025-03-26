package com.example.kuhidbs.dto.Fund.staff;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RStaffDTO {

    private String changeDate; // 변경일자 (YYYY-MM-DD)
    private String previousStaff; // 변경 전 운용인력
    private String currentStaff; // 변경 후 운용인력
    private String resignDate; // 퇴사일자 (YYYY-MM-DD)
    private String reasonAndSanction; // 사유 및 제재 내역

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
