package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_FUNDFNC_TBL") // 테이블 이름 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundFinancial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "FUNDFNC_ID", nullable = false)
    private Long fundFncId; // 조합재무제표 고유번호 (PK, Auto Increment)

    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund; // 조합 고유번호 (FK)

    @Column(name = "FNC_YEAR", length = 40)
    private String financialYear; // 회계연도 (YYYY-12)

    // ✅ 재무 상태
    @Column(name = "VENT_AST")
    private Long ventureAssets; // 창투자산

    @Column(name = "ETC_AST")
    private Long otherAssets; // 기타자산

    @Column(name = "TOT_AST")
    private Long totalAssets; // 자산총계

    @Column(name = "CUR_LIAB")
    private Long currentLiabilities; // 유동부채

    @Column(name = "ETC_LIAB")
    private Long otherLiabilities; // 기타부채

    @Column(name = "TOT_LIAB")
    private Long totalLiabilities; // 부채총계

    @Column(name = "CAPITAL")
    private Long capital; // 출자금

    @Column(name = "RET_EARN")
    private Long retainedEarnings; // 이익잉여

    @Column(name = "TOT_CAPITAL")
    private Long totalCapital; // 자본총계

    // ✅ 손익 계산
    @Column(name = "OPERATING_REVENUE")
    private Long operatingRevenue; // 영업수익

    @Column(name = "OPERATING_EXPENSE")
    private Long operatingExpense; // 영업비용

    @Column(name = "NET_PROFIT")
    private Long netProfit; // 당기순이익

    // ✅ 영업비용

    @Column(name = "INVESTMENT_IMPAIRMENT_LOSS", precision = 10, scale = 1)
    private BigDecimal investmentImpairmentLoss; // 투자주식손상차손

    @Column(name = "MANAGEMENT_FEE", precision = 10, scale = 1)
    private BigDecimal managementFee; // 관리보수

    @Column(name = "PERFORMANCE_FEE", precision = 10, scale = 1)
    private BigDecimal performanceFee; // 성과보수

    @Column(name = "CUSTODY_MANAGEMENT_FEE", precision = 10, scale = 1)
    private BigDecimal custodyManagementFee; // 수탁관리보수


    @Column(name = "AUDIT_FEE", precision = 10, scale = 1)
    private BigDecimal auditFee; // 회계감사수수료

    @Column(name = "MISC_EXPENSE", precision = 10, scale = 1)
    private BigDecimal miscExpense; // 기타 비용
}
