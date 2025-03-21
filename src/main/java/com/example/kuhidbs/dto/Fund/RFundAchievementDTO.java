package com.example.kuhidbs.dto.Fund;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFundAchievementDTO {

    private Long achievementId;
    private String fundId; // 펀드 ID

    private String mandatoryPurpose; // 의무 투자 목적 (예: 초기창업기업)
    private String mainInvest1Purpose; // 주목적 투자 1 목적
    private String mainInvest2Purpose; // 주목적 투자 2 목적
    private String mainInvest3Purpose; // 주목적 투자 3 목적
    private String specialInvest1Purpose; // 특수목적 투자 1 목적
    private String specialInvest2Purpose; // 특수목적 투자 2 목적
    private String specialInvest3Purpose; // 특수목적 투자 3 목적

    // 🔥 의무 투자
    private Long mandatoryAmount; //금액
    private Double mandatoryRatio; //비율
    private String mandatoryCriteria; //기준대상 (출자약정액, 투자금액, 기타)
    private Double mandatoryCriteriaRatio; //기준대상의 비율
    private Long mandatoryTargetAmount; //대상금액


    // 🔥 주목적 투자 1
    private Long mainInvest1Amount;
    private Double mainInvest1Ratio;
    private String mainInvest1Criteria;
    private Double mainInvest1CriteriaRatio;
    private Long mainInvest1TargetAmount;

    // 🔥 주목적 투자 2
    private Long mainInvest2Amount;
    private Double mainInvest2Ratio;
    private String mainInvest2Criteria;
    private Double mainInvest2CriteriaRatio;
    private Long mainInvest2TargetAmount;

    // 🔥 주목적 투자 3
    private Long mainInvest3Amount;
    private Double mainInvest3Ratio;
    private String mainInvest3Criteria;
    private Double mainInvest3CriteriaRatio;
    private Long mainInvest3TargetAmount;

    // 🔥 특수목적 투자 1
    private Long specialInvest1Amount;
    private Double specialInvest1Ratio;
    private String specialInvest1Criteria;
    private Double specialInvest1CriteriaRatio;
    private Long specialInvest1TargetAmount;

    // 🔥 특수목적 투자 2
    private Long specialInvest2Amount;
    private Double specialInvest2Ratio;
    private String specialInvest2Criteria;
    private Double specialInvest2CriteriaRatio;
    private Long specialInvest2TargetAmount;

    // 🔥 특수목적 투자 3
    private Long specialInvest3Amount;
    private Double specialInvest3Ratio;
    private String specialInvest3Criteria;
    private Double specialInvest3CriteriaRatio;
    private Long specialInvest3TargetAmount;
}
