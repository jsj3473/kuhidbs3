package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KUH_SHRHOLD_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shareholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHRHOLD_ID", nullable = false, unique = true)
    private Long shareholderId; // 주주구성개인번호 (PK)

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company; // 회사 고유번호 (FK)

    @Column(name = "SHRHOLD_NM1", length = 8)
    private String shareholderName1; // 주주명칭1

    @Column(name = "SHRHOLD_CNT1")
    private Long shareholderCount1; // 주식수1

    @Column(name = "SHRHOLD_NM2", length = 8)
    private String shareholderName2; // 주주명칭2

    @Column(name = "SHRHOLD_CNT2")
    private Long shareholderCount2; // 주식수2

    @Column(name = "SHRHOLD_NM3", length = 8)
    private String shareholderName3; // 주주명칭3

    @Column(name = "SHRHOLD_CNT3")
    private Long shareholderCount3; // 주식수3

    @Column(name = "SHRHOLD_NM4", length = 8)
    private String shareholderName4; // 주주명칭4

    @Column(name = "SHRHOLD_CNT4")
    private Long shareholderCount4; // 주식수4

    @Column(name = "SHRHOLD_NM5", length = 8)
    private String shareholderName5; // 주주명칭5

    @Column(name = "SHRHOLD_CNT5")
    private Long shareholderCount5; // 주식수5

    @Column(name = "SHRHOLD_NM6", length = 8)
    private String shareholderName6; // 주주명칭6

    @Column(name = "SHRHOLD_CNT6")
    private Long shareholderCount6; // 주식수6

    @Column(name = "SHRHOLD_NM7", length = 8)
    private String shareholderName7; // 주주명칭7

    @Column(name = "SHRHOLD_CNT7")
    private Long shareholderCount7; // 주식수7

    @Column(name = "SHRHOLD_NM8", length = 8)
    private String shareholderName8; // 주주명칭8

    @Column(name = "SHRHOLD_CNT8")
    private Long shareholderCount8; // 주식수8
}
