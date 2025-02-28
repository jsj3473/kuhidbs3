package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.Fund.Fund;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "kuh_member_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundMem  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false, updatable = false)
    private Long memberId;  // 조합원명고유번호 (PK, 자동 증가)

    @ManyToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund;  // 조합고유번호 (FK)

    @Column(name = "MEMBER_NM", length = 2000)
    private String memberName;  // 조합원

    @Column(name = "MEMBER_TP", length = 100)
    private String memberType;  // 조합원유형

    @Column(name = "CONTACT", length = 100)
    private String contact;  // 연락처

    @Column(name = "BUSINESS_REG_NO", length = 100)
    private String regNo;  // 사업자등록번호 or 주민번호

    @Column(name = "ADDRESS", length = 100)
    private String address;  // 주소

    @Column(name = "CMTD_UNIT_PRICE")
    private Long committedUnitPrice;  // 출자금액

    @Column(name = "CONTRIB_RATE", precision = 5, scale = 2)
    private BigDecimal contributionRate;  // 출자비율
}
