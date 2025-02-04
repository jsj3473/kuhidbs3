package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_STCUP_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recovery extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 방식
    @Column(name = "STCUP_ID", nullable = false)
    private Long recoveryId; // 회수 고유 번호 (PK)

    @ManyToOne
    @JoinColumn(name = "INV_ID", nullable = false)
    private Investment investment; // 투자 고유 번호 (FK)

    @Column(name = "STCUP_DT", nullable = false)
    private String recoveryDate; // 일자

    @Column(name = "STCUP_CNT", nullable = false)
    private Long recoveryCount; // 매각 수량

    @Column(name = "STCUP_UNIT_PRICE")
    private Long recoveryUnitPrice; // 매각 단가

    @Column(name = "STCUP_EQT_RATE", precision = 5, scale = 2)
    private BigDecimal recoveryEquityRate; // 지분율

    @Column(name = "STCUP_FUND_RTN")
    private Long fundReturn; // 펀드 수익

    @Column(name = "STCUP_KUH_RTN")
    private Long kuhReturn; // KUH 수익
}
