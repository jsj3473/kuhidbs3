package com.example.kuhidbs.dto.Fund;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class REmploymentDTO {
    private Long investmentId; // 투자 ID
    private String companyNm; //투자기업
    private String initialInvestmentDate; // 최초 투자일자
    private Integer initialEmployeeCount; // 최초 투자 시점 인력 수
    private Integer latestEmployeeCount; // 최신 인력 수
    private String finalRecoveryDate; // 최종 회수일자
    private Integer finalEmployeeCount; // 최종 회수 시점 인력 수
    private Integer financialYear; // 기준년도
    private String financialHalf; // 기준반기
}
