package com.example.kuhidbs.dto.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class REmploymentChangeDTO {

    private String investmentCompany; // 투자기업
    private String investmentDate; // 투자일자

    private Integer initialEmployeeCount; // 최초인력수
    private Integer referenceEmployeeCount; // 기준인력수

    private String finalRecoveryDate; // 최종회수일자
    private Integer recoveryEmployeeCount; // 회수시점인력수
}
