package com.example.kuhidbs.dto.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundMem;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFundMemDTO {
    private String fundId;  // 조합고유번호 (FK, Fund 엔터티의 ID)
    private String memberName;  // 조합원명
    private String memberType;  // 조합원유형
    private String contact;  // 연락처
    private String regNo;  // 사업자등록번호 or 주민번호
    private String address;  // 주소
    private Long committedUnitPrice;  // 출자금액
    private BigDecimal contributionRate;  // 출자비율
}
