package com.example.kuhidbs.dto.Fund;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CStaffDTO {

    private Long fundId; // 조합 고유번호 (FK)
    private String changeDate; // 변경일자 (YYYY-MM-DD)
    private String previousStaff; // 변경 전 운용인력
    private String currentStaff; // 변경 후 운용인력
    private String resignDate; // 퇴사일자 (YYYY-MM-DD)
    private String reasonAndSanction; // 사유 및 제재 내역
}
