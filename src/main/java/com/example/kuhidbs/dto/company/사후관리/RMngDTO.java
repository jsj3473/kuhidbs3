package com.example.kuhidbs.dto.company.사후관리;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RMngDTO {

    private String evalGrade;
    private Integer year;
    private Integer halfYear;
    private String businessProgress1;
    private String businessProgress2;
    private String businessProgress3;
    private String businessProgress4;
    private String businessProgress5;
    private String managementStatus1;
    private String managementStatus2;
    private String exitPlan;
    private String exitEstimation;
}
