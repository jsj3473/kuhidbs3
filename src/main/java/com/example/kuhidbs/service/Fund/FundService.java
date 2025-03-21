package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.repository.Fund.FundAchievementRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundService {
    private static final Logger logger = LoggerFactory.getLogger(FundService.class);
    private final FundRepository fundRepository;
    private final FundAchievementRepository fundAchievementRepository;
    private final InvestmentAssetSummaryRepository investmentAssetSummaryRepository;
    // 펀드 생성
    public Fund createFund(CFundDTO dto) {
        // 1️⃣ Fund 엔티티 생성
        Fund fund = Fund.builder()
                .fundId(dto.getFundId()) // 조합 고유번호
                .fundName(dto.getFundName()) // 조합명
                .fundNameDetail(dto.getFundNameDetail()) // 세부 조합명
                .establishmentDate(dto.getEstablishmentDate()) // 설립일자 (YYYY-MM-DD)
                .duration(dto.getDuration()) // 존속기간
                .durationStartDate(dto.getDurationStartDate()) // 존속기간 시작일 (YYYY-MM-DD)
                .durationEndDate(dto.getDurationEndDate()) // 존속기간 종료일 (YYYY-MM-DD)
                .investmentDuration(dto.getInvestmentDuration()) // 투자기간
                .investmentStartDate(dto.getInvestmentStartDate()) // 투자기간 시작일 (YYYY-MM-DD)
                .investmentEndDate(dto.getInvestmentEndDate()) // 투자기간 종료일 (YYYY-MM-DD)
                .committedTotalPrice(dto.getCommittedTotalPrice()) // 약정 총액
                .unitPrice(dto.getUnitPrice()) // 1좌당 금액
                .fundOrganizationType(dto.getFundOrganizationType()) // 투자기구 유형
                .paymentType(dto.getPaymentType()) // 납입방법
                .leadFundManager(dto.getLeadFundManager()) // 대펀
                .coreIvtManager(dto.getCoreIvtManager()) // 핵운
                .trusteeCorporation(dto.getTrusteeCorporation()) // 업무수탁법인
                .administrationCorporation(dto.getAdministrationCorporation()) // 사무수탁법인
                .targetReturnRate(dto.getTargetReturnRate()) // 기준 수익률
                .performanceFeeRate(dto.getPerformanceFeeRate()) // 성과 보수율
                .managementFeeInvestmentPeriod(dto.getManagementFeeInvestmentPeriod()) // 관리보수 (투자기간)
                .managementFeeManagementPeriod(dto.getManagementFeeManagementPeriod()) // 관리보수 (운영기간)
                .agreementCriteria(dto.getAgreementCriteria()) // 약정기준여부
                .incentiveCondition(dto.getIncentiveCondition()) // 인센티브 조건
                .priorLossGP(dto.getPriorLossGP()) // 우선손실충당 GP
                .priorLossLP(dto.getPriorLossLP()) // 우선손실충당 LP
                .liquidationStatus(dto.getLiquidationStatus()) // 청산 여부
                .liquidationDate(dto.getLiquidationDate()) // 청산일자 (YYYY-MM-DD)
                .mandatoryPurpose(dto.getMandatoryPurpose()) //의무투자
                .mainInvest1Purpose(dto.getMainInvest1Purpose()) //주목적1
                .mainInvest2Purpose(dto.getMainInvest2Purpose()) //주목적2
                .mainInvest3Purpose(dto.getMainInvest3Purpose()) //주목적3
                .specialInvest1Purpose(dto.getSpecialInvest1Purpose()) //특수목적1
                .specialInvest2Purpose(dto.getSpecialInvest2Purpose()) //특수목적2
                .specialInvest3Purpose(dto.getSpecialInvest3Purpose()) //특수목적3
                .build();

        Fund savedFund = fundRepository.save(fund);
        logger.info("[INFO] 펀드 생성 완료 - 펀드 ID: {}", savedFund.getFundId());

        // 2️⃣ FundAchievement 생성 (별도 함수 호출)
        createFundAchievement(savedFund, dto);

        return savedFund;
    }

    /**
     * 펀드 정보 수정
     */
    @Transactional
    public UFundDTO updateFundInfo(UFundDTO updatedFundInfo) {
        Fund fund = fundRepository.findById(updatedFundInfo.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펀드가 존재하지 않습니다: " + updatedFundInfo.getFundId()));

        // 기존 펀드 정보 업데이트
        fund.setLiquidationStatus(updatedFundInfo.getLiquidationStatus());
        fund.setLiquidationDate(updatedFundInfo.getLiquidationDate());
        fund.setFundName(updatedFundInfo.getFundName());
        fund.setFundNameDetail(updatedFundInfo.getFundNameDetail());
        fund.setEstablishmentDate(updatedFundInfo.getEstablishmentDate());
        fund.setDuration(updatedFundInfo.getDuration());
        fund.setDurationStartDate(updatedFundInfo.getDurationStartDate());
        fund.setDurationEndDate(updatedFundInfo.getDurationEndDate());
        fund.setInvestmentDuration(updatedFundInfo.getInvestmentDuration());
        fund.setInvestmentStartDate(updatedFundInfo.getInvestmentStartDate());
        fund.setInvestmentEndDate(updatedFundInfo.getInvestmentEndDate());
        fund.setCommittedTotalPrice(updatedFundInfo.getCommittedTotalPrice());
        fund.setUnitPrice(updatedFundInfo.getUnitPrice());
        fund.setFundOrganizationType(updatedFundInfo.getFundOrganizationType());
        fund.setPaymentType(updatedFundInfo.getPaymentType());
        fund.setLeadFundManager(updatedFundInfo.getLeadFundManager());
        fund.setCoreIvtManager(updatedFundInfo.getCoreIvtManager());
        fund.setTrusteeCorporation(updatedFundInfo.getTrusteeCorporation());
        fund.setAdministrationCorporation(updatedFundInfo.getAdministrationCorporation());
        fund.setTargetReturnRate(updatedFundInfo.getTargetReturnRate());
        fund.setPerformanceFeeRate(updatedFundInfo.getPerformanceFeeRate());
        fund.setManagementFeeInvestmentPeriod(updatedFundInfo.getManagementFeeInvestmentPeriod());
        fund.setManagementFeeManagementPeriod(updatedFundInfo.getManagementFeeManagementPeriod());
        fund.setIncentiveCondition(updatedFundInfo.getIncentiveCondition());
        fund.setPriorLossGP(updatedFundInfo.getPriorLossGP());
        fund.setPriorLossLP(updatedFundInfo.getPriorLossLP());
        fund.setMandatoryPurpose(updatedFundInfo.getMandatoryPurpose());
        fund.setMainInvest1Purpose(updatedFundInfo.getMainInvest1Purpose());
        fund.setMainInvest2Purpose(updatedFundInfo.getMainInvest2Purpose());
        fund.setMainInvest3Purpose(updatedFundInfo.getMainInvest3Purpose());
        fund.setSpecialInvest1Purpose(updatedFundInfo.getSpecialInvest1Purpose());
        fund.setSpecialInvest2Purpose(updatedFundInfo.getSpecialInvest2Purpose());
        fund.setSpecialInvest3Purpose(updatedFundInfo.getSpecialInvest3Purpose());

        fundRepository.save(fund);
        logger.info("[UPDATE] 펀드 정보 수정 완료 - fundId: {}", updatedFundInfo.getFundId());

        return mapToDTO(fund);
    }

    /**
     * Entity → DTO 변환
     */
    private UFundDTO mapToDTO(Fund fund) {
        return UFundDTO.builder()
                .fundId(fund.getFundId())
                .liquidationStatus(fund.getLiquidationStatus())
                .liquidationDate(fund.getLiquidationDate())
                .fundName(fund.getFundName())
                .fundNameDetail(fund.getFundNameDetail())
                .establishmentDate(fund.getEstablishmentDate())
                .duration(fund.getDuration())
                .durationStartDate(fund.getDurationStartDate())
                .durationEndDate(fund.getDurationEndDate())
                .investmentDuration(fund.getInvestmentDuration())
                .investmentStartDate(fund.getInvestmentStartDate())
                .investmentEndDate(fund.getInvestmentEndDate())
                .committedTotalPrice(fund.getCommittedTotalPrice())
                .unitPrice(fund.getUnitPrice())
                .fundOrganizationType(fund.getFundOrganizationType())
                .paymentType(fund.getPaymentType())
                .leadFundManager(fund.getLeadFundManager())
                .coreIvtManager(fund.getCoreIvtManager())
                .trusteeCorporation(fund.getTrusteeCorporation())
                .administrationCorporation(fund.getAdministrationCorporation())
                .targetReturnRate(fund.getTargetReturnRate())
                .performanceFeeRate(fund.getPerformanceFeeRate())
                .managementFeeInvestmentPeriod(fund.getManagementFeeInvestmentPeriod())
                .managementFeeManagementPeriod(fund.getManagementFeeManagementPeriod())
                .incentiveCondition(fund.getIncentiveCondition())
                .priorLossGP(fund.getPriorLossGP())
                .priorLossLP(fund.getPriorLossLP())
                .mandatoryPurpose(fund.getMandatoryPurpose())
                .mainInvest1Purpose(fund.getMainInvest1Purpose())
                .mainInvest2Purpose(fund.getMainInvest2Purpose())
                .mainInvest3Purpose(fund.getMainInvest3Purpose())
                .specialInvest1Purpose(fund.getSpecialInvest1Purpose())
                .specialInvest2Purpose(fund.getSpecialInvest2Purpose())
                .specialInvest3Purpose(fund.getSpecialInvest3Purpose())
                .build();
    }


    private void createFundAchievement(Fund fund, CFundDTO dto) {
        FundAchievement fundAchievement = FundAchievement.builder()
                .fund(fund) // 외래키(Fund) 연결

                // 🔥 의무 투자
                .mandatoryAmount(0L)
                .mandatoryCriteria(dto.getMandatoryCriteria())
                .mandatoryCriteriaRatio(dto.getMandatoryCriteriaRatio())
                .mandatoryTargetAmount(determineTotal(fund, dto.getMandatoryCriteria()))

                // 🔥 주목적 투자 1
                .mainInvest1Amount(0L)
                .mainInvest1Criteria(dto.getMainInvest1Criteria())
                .mainInvest1CriteriaRatio(dto.getMainInvest1CriteriaRatio())
                .mainInvest1TargetAmount(determineTotal(fund, dto.getMainInvest1Criteria()))

                // 🔥 주목적 투자 2
                .mainInvest2Amount(0L)
                .mainInvest2Criteria(dto.getMainInvest2Criteria())
                .mainInvest2CriteriaRatio(dto.getMainInvest2CriteriaRatio())
                .mainInvest2TargetAmount(determineTotal(fund, dto.getMainInvest2Criteria()))

                // 🔥 주목적 투자 3
                .mainInvest3Amount(0L)
                .mainInvest3Criteria(dto.getMainInvest3Criteria())
                .mainInvest3CriteriaRatio(dto.getMainInvest3CriteriaRatio())
                .mainInvest3TargetAmount(determineTotal(fund, dto.getMainInvest3Criteria()))

                // 🔥 특수목적 투자 1
                .specialInvest1Amount(0L)
                .specialInvest1Criteria(dto.getSpecialInvest1Criteria())
                .specialInvest1CriteriaRatio(dto.getSpecialInvest1CriteriaRatio())
                .specialInvest1TargetAmount(determineTotal(fund, dto.getSpecialInvest1Criteria()))

                // 🔥 특수목적 투자 2
                .specialInvest2Amount(0L)
                .specialInvest2Criteria(dto.getSpecialInvest2Criteria())
                .specialInvest2CriteriaRatio(dto.getSpecialInvest2CriteriaRatio())
                .specialInvest2TargetAmount(determineTotal(fund, dto.getSpecialInvest2Criteria()))

                // 🔥 특수목적 투자 3
                .specialInvest3Amount(0L)
                .specialInvest3Criteria(dto.getSpecialInvest3Criteria())
                .specialInvest3CriteriaRatio(dto.getSpecialInvest3CriteriaRatio())
                .specialInvest3TargetAmount(determineTotal(fund, dto.getSpecialInvest3Criteria()))

                .build();

        fundAchievementRepository.save(fundAchievement);
        logger.info("[INFO] FundAchievement 데이터 생성 완료 - 펀드 ID: {}", fund.getFundId());
    }

    private Long determineTotal(Fund fund, String criteria) {
        if ("출자약정액".equals(criteria)) {
            return fund.getCommittedTotalPrice() != null ? fund.getCommittedTotalPrice() : 0L;
        } else if ("투자 잔액".equals(criteria)) {
            return (long) (fund.getCommittedTotalPrice()*0.8);
        }
        return 0L; // 기본값
    }


    public List<RFundNameDTO> getAllFundIdAndName() {
        List<Object[]> results = fundRepository.findAllFundIdAndName();

        return results.stream()
                .map(row -> new RFundNameDTO((String) row[0], (String) row[1]))
                .collect(Collectors.toList());
    }

    public RFundDTO getFundById(String fundId) {
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new RuntimeException("Fund not found with ID: " + fundId));

        return RFundDTO.builder()
                .fundId(fund.getFundId())
                .fundName(fund.getFundName())
                .fundNameDetail(fund.getFundNameDetail())
                .establishmentDate(fund.getEstablishmentDate())
                .duration(fund.getDuration())
                .durationStartDate(fund.getDurationStartDate())
                .durationEndDate(fund.getDurationEndDate())
                .investmentDuration(fund.getInvestmentDuration())
                .investmentStartDate(fund.getInvestmentStartDate())
                .investmentEndDate(fund.getInvestmentEndDate())
                .committedTotalPrice(fund.getCommittedTotalPrice())
                .unitPrice(fund.getUnitPrice())
                .fundOrganizationType(fund.getFundOrganizationType())
                .paymentType(fund.getPaymentType())
                .leadFundManager(fund.getLeadFundManager())
                .coreIvtManager(fund.getCoreIvtManager())
                .trusteeCorporation(fund.getTrusteeCorporation())
                .administrationCorporation(fund.getAdministrationCorporation())
                .targetReturnRate(fund.getTargetReturnRate())
                .performanceFeeRate(fund.getPerformanceFeeRate())
                .managementFeeInvestmentPeriod(fund.getManagementFeeInvestmentPeriod())
                .managementFeeManagementPeriod(fund.getManagementFeeManagementPeriod())
                .incentiveCondition(fund.getIncentiveCondition())
                .priorLossGP(fund.getPriorLossGP())
                .priorLossLP(fund.getPriorLossLP())
                .liquidationStatus(fund.getLiquidationStatus())
                .liquidationDate(fund.getLiquidationDate())
                .mandatoryPurpose(fund.getMandatoryPurpose())
                .mainInvest1Purpose(fund.getMainInvest1Purpose())
                .mainInvest2Purpose(fund.getMainInvest2Purpose())
                .mainInvest3Purpose(fund.getMainInvest3Purpose())
                .specialInvest1Purpose(fund.getSpecialInvest1Purpose())
                .specialInvest2Purpose(fund.getSpecialInvest2Purpose())
                .specialInvest3Purpose(fund.getSpecialInvest3Purpose())
                .build();
    }
}

