package com.example.kuhidbs.entity;

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
public class Audit {

    @Id
    @Column(name = "AUDIT_ID", length = 8, nullable = false)
    private String auditId; // 회계감사 고유번호 (PK)

    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund; // 조합 고유번호 (FK)

    @Column(name = "CHNG_DT")
    private String changeDate; // 변경일자 (YYYY-MM-DD)

    @Column(name = "AUDIT_NM", length = 8)
    private String auditorName; // 회계감사인명

    @Column(name = "CHNG_RESN", length = 200)
    private String changeReason; // 변경사유
}
