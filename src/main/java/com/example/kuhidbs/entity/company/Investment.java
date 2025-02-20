package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.Fund.Fund;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_INV_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 방식
    @Column(name = "INV_ID", nullable = false)
    private Long investmentId; // 투자 고유 번호

    // 🔥 다대일(Many-to-One) 관계 설정
    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)  // 🔥 외래 키(FK) 설정
    private Fund fund; // 투자 재원 (Fund 엔터티와 연결)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "INV_PRDT", length = 100)
    private String investmentProduct; // 투자 상품

    @Column(name = "INV_SUM_PRICE")
    private Long investmentSumPrice; // 투자 금액

    @Column(name = "SHR_CNT")
    private Long shareCount; // 주식 수량

    @Column(name = "INV_UNIT_PRICE")
    private Long investmentUnitPrice; // 투자 단가

    @Column(name = "INV_EQT_RATE", precision = 5, scale = 2)
    private BigDecimal equityRate; // 지분율

    @Column(name = "INV_VAL")
    private Long investmentValue; // 투자 밸류

    @Column(name = "TAN_INV", length = 100)
    private String tangibleInvestment; // 현물 투자

    @Column(name = "INV_STEP")
    private String investmentStep; // 투자 단계

    @Column(name = "INV_DT", length = 100)
    private String investmentDate; // 투자 일자

    @Column(name = "INV_EMP", length = 100)
    private String investmentEmployee; // 투자 담당자

    @Column(name = "TOT_SHR")
    private Long totalShares; // 발행 주식 수
}
