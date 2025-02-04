package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "KUH_FNC_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Financial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 (Auto Increment)
    @Column(name = "FNC_ID", nullable = false)
    private Long financialStatementId;

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "FNC_YEAR")
    private Integer financialYear;

    @Column(name = "FNC_HALF")
    private Integer financialHalf;

    @Column(name = "REVENUE")
    private Integer revenue;

    @Column(name = "OPER_PROFIT")
    private Integer operatingProfit;

    @Column(name = "NET_INCOME")
    private Integer netIncome;

    @Column(name = "TOT_AST")
    private Integer totalAssets;

    @Column(name = "TOT_CAPITAL")
    private Integer totalCapital;

    @Column(name = "CAPITAL")
    private Integer capital;

    @Column(name = "EMP_CNT")
    private Integer employeeCount;

    @Column(name = "DEBT_CAPITAL")
    private Integer totalDebt;
}
