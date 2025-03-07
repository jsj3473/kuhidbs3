package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.Fund.Fund;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "FUND_ACHIEVEMENT")
public class FundAchievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACHIEVEMENT_ID")
    private Long achievementId;

    @OneToOne
    @JoinColumn(name = "FUND_ID", nullable = false)
    private Fund fund; // 해당 펀드와 1:1 매칭

    // 🔥 의무 투자
    @Column(name = "MANDATORY_AMOUNT", nullable = false)
    private Long mandatoryAmount = 0L; // 의무 투자 금액

    @Column(name = "MANDATORY_RATIO", nullable = false)
    private Double mandatoryRatio = 0.0; // 의무 투자 비율

    @Column(name = "MANDATORY_CRITERIA", nullable = false)
    private String mandatoryCriteria; // 의무 투자 기준

    @Column(name = "MANDATORY_CRITERIA_RATIO", nullable = false)
    private Double mandatoryCriteriaRatio = 0.0; // 의무 투자 기준 비율

    @Column(name = "MANDATORY_TOTAL", nullable = false)
    private Long mandatoryTotal = 0L; // 의무 투자 총액

    @Column(name = "MANDATORY_TARGET_AMOUNT", nullable = false)
    private Long mandatoryTargetAmount = 0L; // 🔥 의무 투자 기준 대상 금액

    // 🔥 주목적 투자 1
    @Column(name = "MAIN_INVEST_1_AMOUNT", nullable = false)
    private Long mainInvest1Amount = 0L;

    @Column(name = "MAIN_INVEST_1_RATIO", nullable = false)
    private Double mainInvest1Ratio = 0.0;

    @Column(name = "MAIN_INVEST_1_CRITERIA", nullable = false)
    private String mainInvest1Criteria;

    @Column(name = "MAIN_INVEST_1_CRITERIA_RATIO", nullable = false)
    private Double mainInvest1CriteriaRatio = 0.0;

    @Column(name = "MAIN_INVEST_1_TOTAL", nullable = false)
    private Long mainInvest1Total = 0L;

    @Column(name = "MAIN_INVEST_1_TARGET_AMOUNT", nullable = false)
    private Long mainInvest1TargetAmount = 0L; // 🔥 주목적 투자 1 기준 대상 금액

    // 🔥 주목적 투자 2
    @Column(name = "MAIN_INVEST_2_AMOUNT", nullable = false)
    private Long mainInvest2Amount = 0L;

    @Column(name = "MAIN_INVEST_2_RATIO", nullable = false)
    private Double mainInvest2Ratio = 0.0;

    @Column(name = "MAIN_INVEST_2_CRITERIA", nullable = false)
    private String mainInvest2Criteria;

    @Column(name = "MAIN_INVEST_2_CRITERIA_RATIO", nullable = false)
    private Double mainInvest2CriteriaRatio = 0.0;

    @Column(name = "MAIN_INVEST_2_TOTAL", nullable = false)
    private Long mainInvest2Total = 0L;

    @Column(name = "MAIN_INVEST_2_TARGET_AMOUNT", nullable = false)
    private Long mainInvest2TargetAmount = 0L; // 🔥 주목적 투자 2 기준 대상 금액

    // 🔥 특수목적 투자 1
    @Column(name = "SPECIAL_INVEST_1_AMOUNT", nullable = false)
    private Long specialInvest1Amount = 0L;

    @Column(name = "SPECIAL_INVEST_1_RATIO", nullable = false)
    private Double specialInvest1Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_1_CRITERIA", nullable = false)
    private String specialInvest1Criteria;

    @Column(name = "SPECIAL_INVEST_1_CRITERIA_RATIO", nullable = false)
    private Double specialInvest1CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_1_TOTAL", nullable = false)
    private Long specialInvest1Total = 0L;

    @Column(name = "SPECIAL_INVEST_1_TARGET_AMOUNT", nullable = false)
    private Long specialInvest1TargetAmount = 0L; // 🔥 특수목적 투자 1 기준 대상 금액

    // 🔥 특수목적 투자 2
    @Column(name = "SPECIAL_INVEST_2_AMOUNT", nullable = false)
    private Long specialInvest2Amount = 0L;

    @Column(name = "SPECIAL_INVEST_2_RATIO", nullable = false)
    private Double specialInvest2Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_2_CRITERIA", nullable = false)
    private String specialInvest2Criteria;

    @Column(name = "SPECIAL_INVEST_2_CRITERIA_RATIO", nullable = false)
    private Double specialInvest2CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_2_TOTAL", nullable = false)
    private Long specialInvest2Total = 0L;

    @Column(name = "SPECIAL_INVEST_2_TARGET_AMOUNT", nullable = false)
    private Long specialInvest2TargetAmount = 0L; // 🔥 특수목적 투자 2 기준 대상 금액

    // 🔥 특수목적 투자 3
    @Column(name = "SPECIAL_INVEST_3_AMOUNT", nullable = false)
    private Long specialInvest3Amount = 0L;

    @Column(name = "SPECIAL_INVEST_3_RATIO", nullable = false)
    private Double specialInvest3Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_3_CRITERIA", nullable = false)
    private String specialInvest3Criteria;

    @Column(name = "SPECIAL_INVEST_3_CRITERIA_RATIO", nullable = false)
    private Double specialInvest3CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_3_TOTAL", nullable = false)
    private Long specialInvest3Total = 0L;

    @Column(name = "SPECIAL_INVEST_3_TARGET_AMOUNT", nullable = false)
    private Long specialInvest3TargetAmount = 0L; // 🔥 특수목적 투자 3 기준 대상 금액
}
