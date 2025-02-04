package com.example.kuhidbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CShrDTO {
    private String companyId; // 회사 고유번호 (CMP_ID)

    private String shareholderName1; // 주주명칭1
    private Long shareholderCount1; // 주식수1

    private String shareholderName2; // 주주명칭2
    private Long shareholderCount2; // 주식수2

    private String shareholderName3; // 주주명칭3
    private Long shareholderCount3; // 주식수3

    private String shareholderName4; // 주주명칭4
    private Long shareholderCount4; // 주식수4

    private String shareholderName5; // 주주명칭5
    private Long shareholderCount5; // 주식수5

    private String shareholderName6; // 주주명칭6
    private Long shareholderCount6; // 주식수6

    private String shareholderName7; // 주주명칭7
    private Long shareholderCount7; // 주식수7

    private String shareholderName8; // 주주명칭8
    private Long shareholderCount8; // 주식수8
}
