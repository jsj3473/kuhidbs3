package com.example.kuhidbs.dto.Fund;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CFundFinancialDTO {

    private String fundId; // 조합 고유번호 (FK)
    private String financialYear; // 회계연도 (YYYY-12)

    // ✅ 재무 상태
    private Long ventureAssets; // 창투자산
    private Long otherAssets; // 기타자산
    private Long totalAssets; // 자산총계
    private Long currentLiabilities; // 유동부채
    private Long otherLiabilities; // 기타부채
    private Long totalLiabilities; // 부채총계
    private Long capital; // 출자금
    private Long retainedEarnings; // 이익잉여
    private Long totalCapital; // 자본총계

    // ✅ 손익 계산
    private Long operatingRevenue; // 영업수익
    private Long operatingExpense; // 영업비용
    private Long netProfit; // 당기순이익

    // ✅ 영업비용 상세
    private BigDecimal investmentImpairmentLoss; // 투자주식손상차손 (소수점 1자리)
    private BigDecimal managementFee; // 관리보수 (소수점 1자리)
    private BigDecimal performanceFee; // 성과보수 (소수점 1자리)
    private BigDecimal custodyManagementFee; // 수탁관리보수 (소수점 1자리)
    private BigDecimal auditFee; // 회계감사수수료 (소수점 1자리)
    private BigDecimal miscExpense; // 기타 비용 (소수점 1자리)
}
