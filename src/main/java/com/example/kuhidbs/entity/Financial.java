package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "financial")
@Getter
@Setter
@NoArgsConstructor
public class Financial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finance_id", nullable = false)
    private Integer financeId; // 재무 및 인력 현황 고유 ID

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 ID (외래키)

    @Column(name = "financial_year", nullable = false)
    private Integer financialYear ; // 연도

    @Column(name = "revenue", nullable = false)
    private BigInteger revenue; // 매출액

    @Column(name = "operating_profit", nullable = false)
    private BigInteger operatingProfit; // 영업이익

    @Column(name = "net_income", nullable = false)
    private BigInteger netIncome; // 당기순이익

    @Column(name = "current_assets", nullable = false)
    private BigInteger currentAssets; // 자산총계

    @Column(name = "current_liabilities", nullable = false)
    private BigInteger currentLiabilities; // 부채총계

    @Column(name = "total_equity", nullable = false)
    private BigInteger totalEquity; // 자본총계

    @Column(name = "capital", nullable = false)
    private BigInteger capital; // 자본금

    @Column(name = "employees", nullable = false)
    private Integer employees; // 임직원 수

    @Column(name = "foreign_patents", nullable = false)
    private Integer foreignPatents; // 해외특허 보유 건수

    @Column(name = "equity_value", nullable = false)
    private BigInteger equityValue; // 당사 보유 지분가치

    @Column(name = "overall_evaluation", columnDefinition = "TEXT")
    private String overallEvaluation; // 종합평가
}
