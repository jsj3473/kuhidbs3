package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundMem;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundService {

    private final FundRepository fundRepository;

    // 펀드 생성
    public Fund createFund(CFundDTO dto) {
        System.out.println(dto.getFundId());
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
                .currentStaff(dto.getCurrentStaff()) // 운용인력 전원
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
                .build();

        return fundRepository.save(fund);
    }

    public List<RFundNameDTO> getAllFundIdAndName() {
        List<Object[]> results = fundRepository.findAllFundIdAndName();

        return results.stream()
                .map(row -> new RFundNameDTO((String) row[0], (String) row[1]))
                .collect(Collectors.toList());
    }


    @Transactional
    public void updateCurrentStaff(String fundId, String currentStaff) {
        fundRepository.updateCurrentStaffByFundId(fundId, currentStaff);
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
                .currentStaff(fund.getCurrentStaff())
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
