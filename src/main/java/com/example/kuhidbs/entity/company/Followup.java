package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KUH_FOL_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Followup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOL_ID", length = 100, nullable = false)
    private Long followupId; // 후속 투자 고유 번호

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "FOL_STRT_DT")
    private String followupStartDate; // 투자 일자

    @Column(name = "FOL_CMP_NM", length = 100)
    private String followupCompanyName; // 투자 기관

    @Column(name = "FOL_PRDT")
    private String followupProduct; // 투자 상품 (ENUM)

    @Column(name = "FOL_SUM_PRICE")
    private Long followupSumPrice; // 투자 금액

    @Column(name = "FOL_SHR_CNT")
    private Long followupShareCount; // 주식 수량

    @Column(name = "FOL_UNIT_PRICE")
    private Long followupUnitPrice; // 투자 단가

    @Column(name = "FOL_INV_VAL")
    private Long followupInvestmentValue; // 투자 밸류

}
