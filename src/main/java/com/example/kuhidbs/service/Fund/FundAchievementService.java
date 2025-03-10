package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.RFundAchievementDTO;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.repository.Fund.FundAchievementRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FundAchievementService {

    private final FundAchievementRepository fundAchievementRepository;

    public FundAchievementService(FundAchievementRepository fundAchievementRepository) {
        this.fundAchievementRepository = fundAchievementRepository;
    }

    // 🔥 특정 펀드 ID에 대한 FundAchievement 정보를 조회하여 DTO로 변환
    public RFundAchievementDTO getFundAchievement(String fundId) {
        FundAchievement fundAchievement = fundAchievementRepository.findByFund_FundId(fundId)
                .orElseThrow(() -> new RuntimeException("Fund Achievement not found for Fund ID: " + fundId));

        return fromEntity(fundAchievement);
    }

    // 🔥 Entity → DTO 변환 메서드
    private RFundAchievementDTO fromEntity(FundAchievement entity) {
        RFundAchievementDTO dto = new RFundAchievementDTO();

        dto.setAchievementId(entity.getAchievementId());
        dto.setFundId(entity.getFund().getFundId());

        dto.setMandatoryAmount(entity.getMandatoryAmount());
        dto.setMandatoryRatio(entity.getMandatoryRatio());
        dto.setMandatoryCriteria(entity.getMandatoryCriteria());
        dto.setMandatoryCriteriaRatio(entity.getMandatoryCriteriaRatio());

        dto.setMainInvest1Amount(entity.getMainInvest1Amount());
        dto.setMainInvest1Ratio(entity.getMainInvest1Ratio());
        dto.setMainInvest1Criteria(entity.getMainInvest1Criteria());
        dto.setMainInvest1CriteriaRatio(entity.getMainInvest1CriteriaRatio());

        dto.setMainInvest2Amount(entity.getMainInvest2Amount());
        dto.setMainInvest2Ratio(entity.getMainInvest2Ratio());
        dto.setMainInvest2Criteria(entity.getMainInvest2Criteria());
        dto.setMainInvest2CriteriaRatio(entity.getMainInvest2CriteriaRatio());

        dto.setSpecialInvest1Amount(entity.getSpecialInvest1Amount());
        dto.setSpecialInvest1Ratio(entity.getSpecialInvest1Ratio());
        dto.setSpecialInvest1Criteria(entity.getSpecialInvest1Criteria());
        dto.setSpecialInvest1CriteriaRatio(entity.getSpecialInvest1CriteriaRatio());

        dto.setSpecialInvest2Amount(entity.getSpecialInvest2Amount());
        dto.setSpecialInvest2Ratio(entity.getSpecialInvest2Ratio());
        dto.setSpecialInvest2Criteria(entity.getSpecialInvest2Criteria());
        dto.setSpecialInvest2CriteriaRatio(entity.getSpecialInvest2CriteriaRatio());

        dto.setSpecialInvest3Amount(entity.getSpecialInvest3Amount());
        dto.setSpecialInvest3Ratio(entity.getSpecialInvest3Ratio());
        dto.setSpecialInvest3Criteria(entity.getSpecialInvest3Criteria());
        dto.setSpecialInvest3CriteriaRatio(entity.getSpecialInvest3CriteriaRatio());

        return dto;
    }
}
