package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "followup_investment")
@Getter
@Setter
@NoArgsConstructor
public class Followup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_investment_id", nullable = false)
    private Integer followInvestmentId; // 후속투자고유ID (AUTO_INCREMENT)

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 고유 ID (Company와 연관 관계)

    @Temporal(TemporalType.DATE)
    @Column(name = "investment_date", nullable = false)
    private Date investmentDate; // 투자일자

    @Column(name = "investment_institution", length = 100, nullable = false)
    private String investmentInstitution; // 투자기관

    @Column(name = "fund_name", length = 100)
    private String fundName; // 펀드명

    @Column(name = "investment_method", length = 50)
    private String investmentMethod; // 투자방법

    @Column(name = "investment_unit_price", precision = 19, scale = 0, nullable = false)
    private BigInteger investmentUnitPrice; // 투자단가

    @Column(name = "acquisition_amount", precision = 19, scale = 0, nullable = false)
    private BigInteger acquisitionAmount; // 투자금액

    @Column(name = "acquisition_ratio", precision = 5, scale = 2, nullable = false)
    private BigDecimal acquisitionRatio; // 인수지분율

    @Column(name = "enterprise_value_at_time", precision = 19, scale = 0)
    private BigInteger enterpriseValueAtTime; // 투자 당시 기업 가치

}
