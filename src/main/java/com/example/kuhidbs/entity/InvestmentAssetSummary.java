package com.example.kuhidbs.entity;

import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.company.Investment;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "KUH_IVT_SMRY_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentAssetSummary extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IVT_SMRY_ID", nullable = false)
    private Long investmentAssetSummaryId;

    @ManyToOne
    @JoinColumn(name = "fund_id", nullable = false)
    private Fund fund;

    @OneToOne
    @JoinColumn(name = "investment_id", nullable = false, unique = true)
    private Investment investment;

    @Column(name = "investment_company")
    private String investmentCompany;

    @Column(name = "investment_date")
    private String investmentDate;

    @Column(name = "investment_product")
    private String investmentProduct;

    @Column(name = "management_fee_target")
    private String managementFeeTarget;

    @Column(name = "investment_amount")
    private Long investmentAmount;

    @Column(name = "reduction_amount")
    private Long reductionAmount;

    @Column(name = "recovered_principal")
    private Long recoveredPrincipal;

    @Column(name = "recovered_profit")
    private Long recoveredProfit;

    @Column(name = "remaining_asset_valuation")
    private Long remainingAssetValuation;

    @Column(name = "evaluation_method")
    private String evaluationMethod;

    @Column(name = "investment_balance")
    private Long investmentBalance;

    @Column(name = "multiple")
    private Double multiple;
}
