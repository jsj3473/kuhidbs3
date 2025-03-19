package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.RFundAchievementDTO;
import com.example.kuhidbs.dto.Fund.UFundDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.repository.Fund.FundAchievementRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FundAchievementService {

    private final FundAchievementRepository fundAchievementRepository;

    private final FundRepository fundRepository;
    public FundAchievementService(FundAchievementRepository fundAchievementRepository,
                                  FundRepository fundRepository) {
        this.fundAchievementRepository = fundAchievementRepository;
        this.fundRepository = fundRepository;
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

    @Transactional
    public void updateInfo(UFundDTO updatedFundInfo) {
        Fund fund = fundRepository.findById(updatedFundInfo.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펀드가 존재하지 않습니다: " + updatedFundInfo.getFundId()));

        FundAchievement achievement = fundAchievementRepository.findByFund(fund)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀드 Achievement 정보가 존재하지 않습니다: " + fund.getFundId()));

        achievement.setMandatoryCriteria(updatedFundInfo.getMandatoryCriteria());
        achievement.setMandatoryCriteriaRatio(updatedFundInfo.getMandatoryCriteriaRatio());

        achievement.setMainInvest1Criteria(updatedFundInfo.getMainInvest1Criteria());
        achievement.setMainInvest1CriteriaRatio(updatedFundInfo.getMainInvest1CriteriaRatio());

        achievement.setMainInvest2Criteria(updatedFundInfo.getMainInvest2Criteria());
        achievement.setMainInvest2CriteriaRatio(updatedFundInfo.getMainInvest2CriteriaRatio());

        achievement.setSpecialInvest1Criteria(updatedFundInfo.getSpecialInvest1Criteria());
        achievement.setSpecialInvest1CriteriaRatio(updatedFundInfo.getSpecialInvest1CriteriaRatio());

        achievement.setSpecialInvest2Criteria(updatedFundInfo.getSpecialInvest2Criteria());
        achievement.setSpecialInvest2CriteriaRatio(updatedFundInfo.getSpecialInvest2CriteriaRatio());

        achievement.setSpecialInvest3Criteria(updatedFundInfo.getSpecialInvest3Criteria());
        achievement.setSpecialInvest3CriteriaRatio(updatedFundInfo.getSpecialInvest3CriteriaRatio());

        fundAchievementRepository.save(achievement);

        // 로깅
        LoggerFactory.getLogger(this.getClass()).info("[UPDATE] 펀드 Achievement 정보 수정 완료 - fundId: {}", fund.getFundId());
    }
}
