package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.Fund.Fund;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KUH_AUDIT_TBL") // 테이블 이름
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "AUDIT_ID", nullable = false)
    private Long auditId; // 회계감사인 고유번호 (PK, Auto Increment)

    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund; // 조합 고유번호 (FK)

    @Column(name = "CHNG_YR")
    private String fncYear; // 회계연도

    @Column(name = "CHNG_DT")
    private String changeDate; // 변경일자 (변경일자)

    @Column(name = "AUDIT_NM", length = 800)
    private String auditorName; // 회계감사인명

    @Column(name = "CHNG_RESN", length = 2000)
    private String changeReason; // 변경사유
}
