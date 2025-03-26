package com.example.kuhidbs.dto.company.bonus;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RBonusDTO {
    private String bonusDate; // 무증날짜
    //private String CreateUser; //데이터 생성자
    private Long changedShareCount; // 변경 주식 수
    private Long unitPrice; // 투자 단가

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
