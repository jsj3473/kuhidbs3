package com.example.kuhidbs.dto.company.무증;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CBonusDTO {

    private String companyId; // 회사고유번호
    private Long investmentId; // 투자 고유 번호 (INV_ID)
    private String bonusDate; // 무증날짜
    //private String CreateUser; //데이터 생성자
    private Long changedShareCount; // 변경 주식 수
    private Long unitPrice; // 투자 단가
}
