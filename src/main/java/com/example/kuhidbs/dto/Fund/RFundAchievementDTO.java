package com.example.kuhidbs.dto.Fund;

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

    // 🔥 의무 투자
    private Long mandatoryAmount; //금액
    private Double mandatoryRatio; //비율
    private String mandatoryCriteria; //기준대상 (출자약정액, 투자금액, 기타)
    private Double mandatoryCriteriaRatio; //기준대상의 비율

    // 🔥 주목적 투자 1
    private Long mainInvest1Amount;
    private Double mainInvest1Ratio;
    private String mainInvest1Criteria;
    private Double mainInvest1CriteriaRatio;

    // 🔥 주목적 투자 2
    private Long mainInvest2Amount;
    private Double mainInvest2Ratio;
    private String mainInvest2Criteria;
    private Double mainInvest2CriteriaRatio;

    // 🔥 특수목적 투자 1
    private Long specialInvest1Amount;
    private Double specialInvest1Ratio;
    private String specialInvest1Criteria;
    private Double specialInvest1CriteriaRatio;

    // 🔥 특수목적 투자 2
    private Long specialInvest2Amount;
    private Double specialInvest2Ratio;
    private String specialInvest2Criteria;
    private Double specialInvest2CriteriaRatio;

    // 🔥 특수목적 투자 3
    private Long specialInvest3Amount;
    private Double specialInvest3Ratio;
    private String specialInvest3Criteria;
    private Double specialInvest3CriteriaRatio;
}
