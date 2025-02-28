package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KUH_BONUS_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bonus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    @Column(name = "BONUS_ID", nullable = false)
    private Long bonusId; // 무상증자 고유번호 (PK)

    @ManyToOne
    @JoinColumn(name = "INV_ID", nullable = false)
    private Investment investment; // 투자 고유 번호 (FK)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "KUH_BONUS_TBL", length = 100)
    private String bonusDate; // 무상증자 일자

    @Column(name = "CHNG_SHR_CNT")
    private Long changedShareCount; // 변경 주식 수

    @Column(name = "UNIT_PRICE")
    private Long unitPrice; // 투자 단가
}
