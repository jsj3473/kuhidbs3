package com.example.kuhidbs.dto.company.mng;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMngDTO {

    private String companyId;
    private Integer manageYear;
    private Integer halfYear;  // ✅ 추가
    private String evalGrade;
    private String businessProgress1;
    private String businessProgress2;
    private String businessProgress3;
    private String businessProgress4;
    private String businessProgress5;
    private String managementStatus1;
    private String managementStatus2;
    private String managementStatus3;
    private String exitPlan1;
    private String exitPlan2;
    private String exitEstimation;
}
