package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_ACNT_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 설정
    @Column(name = "ACNT_ID", nullable = false, updatable = false)
    private Long accountId; // 계좌 고유 번호 (자동 증가)

    @ManyToOne
    @JoinColumn(name = "INV_ID", nullable = false)
    private Investment investment; // 투자 고유 번호

    @Column(name = "UNIT_PRICE", nullable = false)
    private Long unitPrice; // 투자 단가

    @Column(name = "HELD_SHR_CNT", nullable = false)
    private Long heldShareCount; // 보유 주식 수

    @Column(name = "TOT_PRINC", nullable = false)
    private Long totalPrincipal; // 투자 원금

    @Column(name = "FUN_TP", length = 100)
    private String functionType; // 실행 함수

    @Column(name = "KUH_EQT_RATE", precision = 5, scale = 2)
    private BigDecimal kuhEquityRate; // KUH 지분율
}
