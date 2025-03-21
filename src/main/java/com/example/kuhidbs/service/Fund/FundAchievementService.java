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
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new RuntimeException("Fund Achievement not found for Fund ID: " + fundId));

        return fromEntity(fundAchievement,fund);
    }

    // 🔥 Entity → DTO 변환 메서드
    private RFundAchievementDTO fromEntity(FundAchievement entity, Fund fund) {
        RFundAchievementDTO dto = new RFundAchievementDTO();

        dto.setAchievementId(entity.getAchievementId());
        dto.setFundId(entity.getFund().getFundId());

        dto.setMandatoryPurpose(fund.getMandatoryPurpose());
        dto.setMainInvest1Purpose(fund.getMainInvest1Purpose());
        dto.setMainInvest2Purpose(fund.getMainInvest2Purpose());
        dto.setSpecialInvest1Purpose(fund.getSpecialInvest1Purpose());
        dto.setSpecialInvest2Purpose(fund.getSpecialInvest2Purpose());
        dto.setSpecialInvest3Purpose(fund.getSpecialInvest3Purpose());

        dto.setMandatoryAmount(entity.getMandatoryAmount());
        dto.setMandatoryRatio(entity.getMandatoryRatio());
        dto.setMandatoryCriteria(entity.getMandatoryCriteria());
        dto.setMandatoryCriteriaRatio(entity.getMandatoryCriteriaRatio());
        dto.setMandatoryTargetAmount(entity.getMandatoryTargetAmount());

        dto.setMainInvest1Amount(entity.getMainInvest1Amount());
        dto.setMainInvest1Ratio(entity.getMainInvest1Ratio());
        dto.setMainInvest1Criteria(entity.getMainInvest1Criteria());
        dto.setMainInvest1CriteriaRatio(entity.getMainInvest1CriteriaRatio());
        dto.setMainInvest1TargetAmount(entity.getMainInvest1TargetAmount());

        dto.setMainInvest2Amount(entity.getMainInvest2Amount());
        dto.setMainInvest2Ratio(entity.getMainInvest2Ratio());
        dto.setMainInvest2Criteria(entity.getMainInvest2Criteria());
        dto.setMainInvest2CriteriaRatio(entity.getMainInvest2CriteriaRatio());
        dto.setMainInvest2TargetAmount(entity.getMainInvest2TargetAmount());

        dto.setMainInvest3Amount(entity.getMainInvest3Amount());
        dto.setMainInvest3Ratio(entity.getMainInvest3Ratio());
        dto.setMainInvest3Criteria(entity.getMainInvest3Criteria());
        dto.setMainInvest3CriteriaRatio(entity.getMainInvest3CriteriaRatio());
        dto.setMainInvest3TargetAmount(entity.getMainInvest3TargetAmount());

        dto.setSpecialInvest1Amount(entity.getSpecialInvest1Amount());
        dto.setSpecialInvest1Ratio(entity.getSpecialInvest1Ratio());
        dto.setSpecialInvest1Criteria(entity.getSpecialInvest1Criteria());
        dto.setSpecialInvest1CriteriaRatio(entity.getSpecialInvest1CriteriaRatio());
        dto.setSpecialInvest1TargetAmount(entity.getSpecialInvest1TargetAmount());

        dto.setSpecialInvest2Amount(entity.getSpecialInvest2Amount());
        dto.setSpecialInvest2Ratio(entity.getSpecialInvest2Ratio());
        dto.setSpecialInvest2Criteria(entity.getSpecialInvest2Criteria());
        dto.setSpecialInvest2CriteriaRatio(entity.getSpecialInvest2CriteriaRatio());
        dto.setSpecialInvest2TargetAmount(entity.getSpecialInvest2TargetAmount());

        dto.setSpecialInvest3Amount(entity.getSpecialInvest3Amount());
        dto.setSpecialInvest3Ratio(entity.getSpecialInvest3Ratio());
        dto.setSpecialInvest3Criteria(entity.getSpecialInvest3Criteria());
        dto.setSpecialInvest3CriteriaRatio(entity.getSpecialInvest3CriteriaRatio());
        dto.setSpecialInvest3TargetAmount(entity.getSpecialInvest3TargetAmount());

        return dto;
    }
}
