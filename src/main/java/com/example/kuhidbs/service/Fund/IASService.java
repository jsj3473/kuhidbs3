package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.RIASDTO;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IASService {

    private final InvestmentAssetSummaryRepository investmentAssetSummaryRepository;

    // 특정 펀드 ID에 해당하는 투자자산총괄 데이터를 조회하여 DTO로 변환
    public List<RIASDTO> getInvestmentAssetSummaryByFundId(String fundId) {
        List<InvestmentAssetSummary> assetSummaries = investmentAssetSummaryRepository.findByFund_FundId(fundId);

        return assetSummaries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 엔터티를 DTO로 변환하는 메서드
    private RIASDTO convertToDTO(InvestmentAssetSummary entity) {
        return RIASDTO.builder()
                .investmentCompany(entity.getInvestmentCompany())
                .investmentDate(entity.getInvestmentDate())
                .investmentProduct(entity.getInvestmentProduct())
                .managementFeeTarget(entity.getManagementFeeTarget())
                .investmentAmount(entity.getInvestmentAmount())
                .reductionAmount(entity.getReductionAmount())
                .recoveredPrincipal(entity.getRecoveredPrincipal())
                .recoveredProfit(entity.getRecoveredProfit())
                .remainingAssetValuation(entity.getRemainingAssetValuation())
                .evaluationMethod(entity.getEvaluationMethod())
                .investmentBalance(entity.getInvestmentBalance())
                .multiple(entity.getMultiple())
                .build();
    }

    public void calculateDerivedFields(InvestmentAssetSummary summary, Account lateAccount) {
        if (summary.getInvestmentAmount() == null) summary.setInvestmentAmount(0L);
        if (summary.getReductionAmount() == null) summary.setReductionAmount(0L);
        if (summary.getRecoveredPrincipal() == null) summary.setRecoveredPrincipal(0L);
        if (summary.getRecoveredProfit() == null) summary.setRecoveredProfit(0L);
        if (summary.getRemainingAssetValuation() == null) summary.setRemainingAssetValuation(0L);

        Long investmentBalance = summary.getInvestmentAmount() - summary.getReductionAmount() - summary.getRecoveredPrincipal();
        // 투자잔액 계산
        summary.setInvestmentBalance(investmentBalance);
        if (lateAccount != null) {
            Long janyeo = investmentBalance * lateAccount.getCurUnitPrice() / lateAccount.getUnitPrice();
            summary.setRemainingAssetValuation(janyeo);
        }
        // 멀티플 계산
        if (summary.getInvestmentAmount() != 0) {
            double multiple = (double) (summary.getRecoveredPrincipal() + summary.getRecoveredProfit() + summary.getRemainingAssetValuation()) / summary.getInvestmentAmount();
            summary.setMultiple(multiple);
        } else {
            summary.setMultiple(0.0);
        }
        investmentAssetSummaryRepository.save(summary);
    }
}
