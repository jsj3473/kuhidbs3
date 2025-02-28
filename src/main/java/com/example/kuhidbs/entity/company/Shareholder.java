package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "KUH_SHRHOLD_TBL")
@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Shareholder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHRHOLD_ID", nullable = false, unique = true)
    private Long shareholderId; // 주주구성개인번호 (PK)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company; // 회사 고유번호 (FK)

    // 주주 및 주식 수 정보
    @Column(name = "SHRHOLD_NM1", length = 200)
    private String shareholderName1;

    @Column(name = "SHRHOLD_CNT1")
    private Long shareholderCount1;

    @Column(name = "SHRHOLD_RATE1", precision = 5, scale = 2)
    private BigDecimal shareholderRate1; // 지분율1

    @Column(name = "SHRHOLD_NM2", length = 200)
    private String shareholderName2;

    @Column(name = "SHRHOLD_CNT2")
    private Long shareholderCount2;

    @Column(name = "SHRHOLD_RATE2", precision = 5, scale = 2)
    private BigDecimal shareholderRate2; // 지분율2

    @Column(name = "SHRHOLD_NM3", length = 200)
    private String shareholderName3;

    @Column(name = "SHRHOLD_CNT3")
    private Long shareholderCount3;

    @Column(name = "SHRHOLD_RATE3", precision = 5, scale = 2)
    private BigDecimal shareholderRate3; // 지분율3

    @Column(name = "SHRHOLD_NM4", length = 200)
    private String shareholderName4;

    @Column(name = "SHRHOLD_CNT4")
    private Long shareholderCount4;

    @Column(name = "SHRHOLD_RATE4", precision = 5, scale = 2)
    private BigDecimal shareholderRate4; // 지분율4

    @Column(name = "SHRHOLD_NM5", length = 200)
    private String shareholderName5;

    @Column(name = "SHRHOLD_CNT5")
    private Long shareholderCount5;

    @Column(name = "SHRHOLD_RATE5", precision = 5, scale = 2)
    private BigDecimal shareholderRate5; // 지분율5

    @Column(name = "SHRHOLD_NM6", length = 200)
    private String shareholderName6;

    @Column(name = "SHRHOLD_CNT6")
    private Long shareholderCount6;

    @Column(name = "SHRHOLD_RATE6", precision = 5, scale = 2)
    private BigDecimal shareholderRate6; // 지분율6

    @Column(name = "SHRHOLD_NM7", length = 200)
    private String shareholderName7;

    @Column(name = "SHRHOLD_CNT7")
    private Long shareholderCount7;

    @Column(name = "SHRHOLD_RATE7", precision = 5, scale = 2)
    private BigDecimal shareholderRate7; // 지분율7

    @Column(name = "SHRHOLD_NM8", length = 200)
    private String shareholderName8;

    @Column(name = "SHRHOLD_CNT8")
    private Long shareholderCount8;

    @Column(name = "SHRHOLD_RATE8", precision = 5, scale = 2)
    private BigDecimal shareholderRate8; // 지분율8

    // 총 발행 주식 수
    @Column(name = "TOT_SHR_CNT")
    private Long totalShareCount;
}
