package com.example.kuhidbs.entity.company;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "KUH_COM_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COM_ID", nullable = false, unique = true)
    private Long combineId; // 동반투자 고유번호

    @ManyToOne
    @JoinColumn(name = "INV_ID", nullable = false)
    private Investment investment; // 투자 고유번호 (FK)

    @Column(name = "COM_CMP_NM", length = 50)
    private String investmentCompanyName; // 투자 기관

    @Column(name = "COM_STRT_DT", length = 50)
    private String investmentStartDate; // 투자 일자

    @Column(name = "COM_UNIT_PRICR")
    private Long investmentUnitPrice; // 투자 단가

    @Column(name = "COM_SUM_PRICE")
    private Long investmentSumPrice; // 투자 금액

    @Column(name = "COM_PRDT", length = 50)
    private String investmentProduct; // 투자 상품

    @Column(name = "COM_EQT_RATE", precision = 5, scale = 2)
    private BigDecimal equityRate; // 지분율

    @Column(name = "COM_CMT", length = 1000)
    private String comment; // 비고

    @Column(name = "INV_STEP", length = 100)
    private String investmentStep; // 투자 단계

    @Column(name = "SHR_CNT")
    private Long shareCount; // 주식 수량
}
