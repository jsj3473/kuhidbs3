package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.entity.Fund.FundMem;
import com.example.kuhidbs.repository.Fund.FundAchievementRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.service.company.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    private void createFundAchievement(Fund fund, CFundDTO dto) {
        FundAchievement fundAchievement = FundAchievement.builder()
                .fund(fund) // 외래키(Fund) 연결

                // 🔥 의무 투자
                .mandatoryAmount(0L)
                .mandatoryCriteria(dto.getMandatoryCriteria())
                .mandatoryCriteriaRatio(dto.getMandatoryCriteriaRatio())
                .mandatoryTargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getMandatoryCriteria(), dto.getMandatoryTotal()),
                        dto.getMandatoryCriteriaRatio()))

                // 🔥 주목적 투자 1
                .mainInvest1Amount(0L)
                .mainInvest1Criteria(dto.getMainInvest1Criteria())
                .mainInvest1CriteriaRatio(dto.getMainInvest1CriteriaRatio())
                .mainInvest1TargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getMainInvest1Criteria(), dto.getMainInvest1Total()),
                        dto.getMainInvest1CriteriaRatio()))

                // 🔥 주목적 투자 2
                .mainInvest2Amount(0L)
                .mainInvest2Criteria(dto.getMainInvest2Criteria())
                .mainInvest2CriteriaRatio(dto.getMainInvest2CriteriaRatio())
                .mainInvest2TargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getMainInvest2Criteria(), dto.getMainInvest2Total()),
                        dto.getMainInvest2CriteriaRatio()))

                // 🔥 특수목적 투자 1
                .specialInvest1Amount(0L)
                .specialInvest1Criteria(dto.getSpecialInvest1Criteria())
                .specialInvest1CriteriaRatio(dto.getSpecialInvest1CriteriaRatio())
                .specialInvest1TargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getSpecialInvest1Criteria(), dto.getSpecialInvest1Total()),
                        dto.getSpecialInvest1CriteriaRatio()))

                // 🔥 특수목적 투자 2
                .specialInvest2Amount(0L)
                .specialInvest2Criteria(dto.getSpecialInvest2Criteria())
                .specialInvest2CriteriaRatio(dto.getSpecialInvest2CriteriaRatio())
                .specialInvest2TargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getSpecialInvest2Criteria(), dto.getSpecialInvest2Total()),
                        dto.getSpecialInvest2CriteriaRatio()))

                // 🔥 특수목적 투자 3
                .specialInvest3Amount(0L)
                .specialInvest3Criteria(dto.getSpecialInvest3Criteria())
                .specialInvest3CriteriaRatio(dto.getSpecialInvest3CriteriaRatio())
                .specialInvest3TargetAmount(calculateTargetAmount(
                        determineTotal(fund, dto.getSpecialInvest3Criteria(), dto.getSpecialInvest3Total()),
                        dto.getSpecialInvest3CriteriaRatio()))
                .build();

        fundAchievementRepository.save(fundAchievement);
        logger.info("[INFO] FundAchievement 데이터 생성 완료 - 펀드 ID: {}", fund.getFundId());
    }

    private Long determineTotal(Fund fund, String criteria, Long dtoTotal) {
        if ("출자약정액".equals(criteria)) {
            return fund.getCommittedTotalPrice() != null ? fund.getCommittedTotalPrice() : 0L;
        } else if ("기타".equals(criteria)) {
            return dtoTotal != null ? dtoTotal : 0L;
        } else if ("투자잔액".equals(criteria)) {
            return getTotalInvestmentAmount(fund);
        }
        return 0L; // 기본값
    }


    private Long getTotalInvestmentAmount(Fund fund) {
        Long totalInvestmentAmount = investmentAssetSummaryRepository
                .sumInvestmentAmountByFund(fund)
                .orElse(0L);

        logger.info("[INFO] 투자잔액 계산 - 펀드 ID: {}, 총 투자잔액: {}", fund.getFundId(), totalInvestmentAmount);
        return totalInvestmentAmount;
    }



    private Long calculateTargetAmount(Long total, Double ratio) {
        if (total == null || ratio == null) {
            return 0L; // 기본값 처리
        }
        return Math.round((total * ratio) / 100); // 80으로 나누기 추가
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
                .build();
    }
}
