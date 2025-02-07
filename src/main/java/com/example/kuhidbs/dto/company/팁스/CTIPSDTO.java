package com.example.kuhidbs.dto.company.팁스;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTIPSDTO {

    private String companyId; // 회사 고유번호 (FK)
    private String tipsComment; // 입력내용
    private String tipsSelectionDate; // 선정일자 (YYYY-MM-DD)
    private String tipsSelectionType; // 선정종류
    private String executionStartDate; // 수행기간 시작일 (YYYY-MM-DD)
    private String executionEndDate; // 수행기간 종료일 (YYYY-MM-DD)
    private String selectionYesNo; // 선정여부
    private String tipsManagementEndDate; // 사후기간 종료일 (YYYY-MM-DD)
    private String successYesNo; // 성공여부
    private String followTips; // 사후성공여부
    private String kips; // 성공정량지표
}
