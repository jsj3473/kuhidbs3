package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.Fund.Fund;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KUH_STAFF_HST") // 테이블 이름
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "STAFF_ID", nullable = false)
    private Long staffId; // 운용인력 고유번호 (PK, Auto Increment)

    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund; // 조합 고유번호 (FK)

    @Column(name = "CHNG_DT", length = 10)
    private String changeDate; // 변경일자 (YYYY-MM-DD)

    @Column(name = "PREV_STAFF", length = 8)
    private String previousStaff; // 변경 전 운용인력

    @Column(name = "CURR_STAFF", length = 8)
    private String currentStaff; // 변경 후 운용인력

    @Column(name = "RESIGN_DATE", length = 10)
    private String resignDate; // 퇴사일자 (YYYY-MM-DD)

    @Column(name = "RESN_SANCT", length = 200)
    private String reasonAndSanction; // 사유 및 제재 내역
}
