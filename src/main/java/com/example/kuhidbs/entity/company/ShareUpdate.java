package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "KUH_SHRUP_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareUpdate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 방식
    @Column(name = "SHRUP_ID", nullable = false)
    private Long shareUpdateId; // 감액 복원 고유 번호

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "INV_ID", nullable = false)
    private Investment investment; // 투자 고유 번호 (ManyToOne 관계)

    @Column(name = "SHRUP_DT", nullable = false)
    private String shareUpdateDate; // 변동 일자

    @Column(name = "SHR_UNIT_VAL", nullable = false)
    private Long shareUnitValue; // kuh 주당 가치

    @Column(name = "CUR_UNIT_VAL", nullable = false)
    private Long curUnitValue; // 현재 주당 가치

    @Column(name = "SHRUP_TP", length = 2, nullable = false)
    private String shareUpdateType; // 감액/복원

    @Column(name = "SHRUP_RESN", length = 1000)
    private String shareUpdateReason; // 사유

    @Column(name = "SHRUP_ACTN", length = 1000)
    private String shareUpdateAction; // 조치 사항
}

