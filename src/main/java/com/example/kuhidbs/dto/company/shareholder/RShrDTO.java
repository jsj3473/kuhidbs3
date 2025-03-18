package com.example.kuhidbs.dto.company.shareholder;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RShrDTO {

    // 주주 및 주식 수 정보
    private String shareholderName1; // 주주명칭1
    private Long shareholderCount1; // 주식수1
    private BigDecimal shareholderRate1; // 지분율1

    private String shareholderName2; // 주주명칭2
    private Long shareholderCount2; // 주식수2
    private BigDecimal shareholderRate2; // 지분율2

    private String shareholderName3; // 주주명칭3
    private Long shareholderCount3; // 주식수3
    private BigDecimal shareholderRate3; // 지분율3

    private String shareholderName4; // 주주명칭4
    private Long shareholderCount4; // 주식수4
    private BigDecimal shareholderRate4; // 지분율4

    private String shareholderName5; // 주주명칭5
    private Long shareholderCount5; // 주식수5
    private BigDecimal shareholderRate5; // 지분율5

    private String shareholderName6; // 주주명칭6
    private Long shareholderCount6; // 주식수6
    private BigDecimal shareholderRate6; // 지분율6

    private String shareholderName7; // 주주명칭7
    private Long shareholderCount7; // 주식수7
    private BigDecimal shareholderRate7; // 지분율7

    private String shareholderName8; // 주주명칭8
    private Long shareholderCount8; // 주식수8
    private BigDecimal shareholderRate8; // 지분율8

    // 총 발행 주식 수
    private Long totalShareCount;
}
