package com.example.kuhidbs.entity;

import com.example.kuhidbs.entity.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "recovery")
@Getter
@Setter
@NoArgsConstructor
public class Recovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recovery_id", nullable = false)
    private Integer recoveryId; // 회수 고유 ID

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 (Company 테이블과 ManyToOne 관계)

    @Temporal(TemporalType.DATE)
    @Column(name = "recovery_date", nullable = false)
    private Date recoveryDate; // 회수 일자

    @Column(name = "recovery_unit_price", nullable = false)
    private Integer recoveryUnitPrice; // 회수 단가

    @Column(name = "investment_method", length = 50, nullable = false)
    private String investmentMethod; // 회수 방법

    @Column(name = "recovery_amount", nullable = false)
    private BigInteger recoveryAmount; // 회수 수익금

    @Column(name = "recovery_ratio", precision = 5, scale = 2, nullable = false)
    private BigDecimal recoveryRatio; // 회수 지분율

    @Column(name = "recovery_income", nullable = false)
    private BigInteger recoveryIncome; // 회수 수입금

    @Column(name = "remaining_investment", nullable = false)
    private BigInteger remainingInvestment; // 투자 잔액

    @Column(name = "investment_reduction", nullable = false)
    private BigInteger investmentReduction; // 투자 감액
}
