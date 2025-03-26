package com.example.kuhidbs.dto.company.tips;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RTIPSDTO {

    private Long tipsId; // 팁스 현황 고유번호
    private String tipsSelectionType; // 팁스종류 선정종류
    private String selectionYesNo; // 선정여부
    private String tipsSelectionDate; // 선정일자
    private String executionStartDate; // 수행기간 시작일
    private String executionEndDate; // 수행기간 종료일
    private String successYesNo; // 성공여부
    private String kips; // 성공정량지표
    private String tipsComment; // 입력내용
    private String tipsManagementEndDate; // 사후기간 종료일
    private String followTips; // 사후성공여부

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;  // 생성한 사람 (사번)
    private String updatedBy;  // 수정한 사람 (사번)
}
