package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

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
    private String financialHalf;

    @Column(name = "REVENUE")
    private BigDecimal revenue; // 매출 (소수점 가능)

    @Column(name = "OPER_PROFIT")
    private BigDecimal operatingProfit; // 영업이익 (소수점 가능)

    @Column(name = "NET_INCOME")
    private BigDecimal netIncome; // 순이익 (소수점 가능)

    @Column(name = "TOT_AST")
    private BigDecimal totalAssets; // 총자산 (소수점 가능)

    @Column(name = "TOT_CAPITAL")
    private BigDecimal totalCapital; // 총자본 (소수점 가능)

    @Column(name = "CAPITAL")
    private BigDecimal capital; // 자본금 (소수점 가능)

    @Column(name = "DEBT_CAPITAL")
    private BigDecimal totalDebt; // 총부채 (소수점 가능)


    @Column(name = "EMP_CNT")
    private Integer employeeCount;
}
