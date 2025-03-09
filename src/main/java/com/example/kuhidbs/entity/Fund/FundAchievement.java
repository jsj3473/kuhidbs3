package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FUND_ACHIEVEMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundAchievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACHIEVEMENT_ID")
    private Long achievementId;

    @OneToOne
    @JoinColumn(name = "FUND_ID")
    private Fund fund; // 해당 펀드와 1:1 매칭

    // 🔥 의무 투자
    @Column(name = "MANDATORY_AMOUNT")
    private Long mandatoryAmount = 0L; // 의무 투자 금액

    @Column(name = "MANDATORY_RATIO")
    private Double mandatoryRatio = 0.0; // 의무 투자 비율

    @Column(name = "MANDATORY_CRITERIA")
    private String mandatoryCriteria; // 의무 투자 기준

    @Column(name = "MANDATORY_CRITERIA_RATIO")
    private Double mandatoryCriteriaRatio = 0.0; // 의무 투자 기준 비율

    @Column(name = "MANDATORY_TARGET_AMOUNT")
    private Long mandatoryTargetAmount = 0L; // 🔥 의무 투자 기준 대상 금액

    // 🔥 주목적 투자 1
    @Column(name = "MAIN_INVEST_1_AMOUNT")
    private Long mainInvest1Amount = 0L;

    @Column(name = "MAIN_INVEST_1_RATIO")
    private Double mainInvest1Ratio = 0.0;

    @Column(name = "MAIN_INVEST_1_CRITERIA")
    private String mainInvest1Criteria;

    @Column(name = "MAIN_INVEST_1_CRITERIA_RATIO")
    private Double mainInvest1CriteriaRatio = 0.0;

    @Column(name = "MAIN_INVEST_1_TARGET_AMOUNT")
    private Long mainInvest1TargetAmount = 0L; // 🔥 주목적 투자 1 기준 대상 금액

    // 🔥 주목적 투자 2
    @Column(name = "MAIN_INVEST_2_AMOUNT")
    private Long mainInvest2Amount = 0L;

    @Column(name = "MAIN_INVEST_2_RATIO")
    private Double mainInvest2Ratio = 0.0;

    @Column(name = "MAIN_INVEST_2_CRITERIA")
    private String mainInvest2Criteria;

    @Column(name = "MAIN_INVEST_2_CRITERIA_RATIO")
    private Double mainInvest2CriteriaRatio = 0.0;

    @Column(name = "MAIN_INVEST_2_TARGET_AMOUNT")
    private Long mainInvest2TargetAmount = 0L; // 🔥 주목적 투자 2 기준 대상 금액

    // 🔥 특수목적 투자 1
    @Column(name = "SPECIAL_INVEST_1_AMOUNT")
    private Long specialInvest1Amount = 0L;

    @Column(name = "SPECIAL_INVEST_1_RATIO")
    private Double specialInvest1Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_1_CRITERIA")
    private String specialInvest1Criteria;

    @Column(name = "SPECIAL_INVEST_1_CRITERIA_RATIO")
    private Double specialInvest1CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_1_TARGET_AMOUNT")
    private Long specialInvest1TargetAmount = 0L; // 🔥 특수목적 투자 1 기준 대상 금액

    // 🔥 특수목적 투자 2
    @Column(name = "SPECIAL_INVEST_2_AMOUNT")
    private Long specialInvest2Amount = 0L;

    @Column(name = "SPECIAL_INVEST_2_RATIO")
    private Double specialInvest2Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_2_CRITERIA")
    private String specialInvest2Criteria;

    @Column(name = "SPECIAL_INVEST_2_CRITERIA_RATIO")
    private Double specialInvest2CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_2_TARGET_AMOUNT")
    private Long specialInvest2TargetAmount = 0L; // 🔥 특수목적 투자 2 기준 대상 금액

    // 🔥 특수목적 투자 3
    @Column(name = "SPECIAL_INVEST_3_AMOUNT")
    private Long specialInvest3Amount = 0L;

    @Column(name = "SPECIAL_INVEST_3_RATIO")
    private Double specialInvest3Ratio = 0.0;

    @Column(name = "SPECIAL_INVEST_3_CRITERIA")
    private String specialInvest3Criteria;

    @Column(name = "SPECIAL_INVEST_3_CRITERIA_RATIO")
    private Double specialInvest3CriteriaRatio = 0.0;

    @Column(name = "SPECIAL_INVEST_3_TARGET_AMOUNT")
    private Long specialInvest3TargetAmount = 0L; // 🔥 특수목적 투자 3 기준 대상 금액
}
