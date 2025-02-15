package com.example.kuhidbs.dto.company.투자상태;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RStatusDTO {

    private String companyId; // 회사 고유 번호
    private String status; // 투자 상태
    private String additionalInfo; // 기타 정보
}
