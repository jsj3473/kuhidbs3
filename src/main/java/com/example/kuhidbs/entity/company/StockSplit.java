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
public class StockSplit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPLIT_ID", length = 100, nullable = false)
    private Long stockSplitId; // 액면분할 고유 번호

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "SPLIT_DATE")
    private String splitDate; // 액면분할 일자

    @Column(name = "PRE_SPLIT_UNIT_PRICE")
    private Long preSplitUnitPrice; // 액면분할 전 주당 가격

    @Column(name = "POST_SPLIT_UNIT_PRICE")
    private Long postSplitUnitPrice; // 액면분할 후 주당 가격

    @Column(name = "SPLIT_RATIO")
    private Double splitRatio; // 액면분할 비율 (예: 2.0이면 1주 → 2주)
}

