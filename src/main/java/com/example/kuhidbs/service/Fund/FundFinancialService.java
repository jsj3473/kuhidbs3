package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.CFundFinancialDTO;
import com.example.kuhidbs.dto.Fund.RFundFinancialDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundFinancial;
import com.example.kuhidbs.repository.Fund.FundFinancialRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundFinancialService {

    private final FundFinancialRepository fundFinancialRepository;
    private final FundRepository fundRepository;

    /**
     * ✅ 재무 정보 생성 함수
     */
    public FundFinancial createFundFinancial(CFundFinancialDTO dto) {
        Fund fund = fundRepository.findById(dto.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 조합 ID: " + dto.getFundId()));

        FundFinancial fundFinancial = FundFinancial.builder()
                .fund(fund)
                .financialYear(dto.getFinancialYear())
                .ventureAssets(dto.getVentureAssets())
                .otherAssets(dto.getOtherAssets())
                .totalAssets(dto.getTotalAssets())
                .currentLiabilities(dto.getCurrentLiabilities())
                .otherLiabilities(dto.getOtherLiabilities())
                .totalLiabilities(dto.getTotalLiabilities())
                .capital(dto.getCapital())
                .retainedEarnings(dto.getRetainedEarnings())
                .totalCapital(dto.getTotalCapital())
                .operatingRevenue(dto.getOperatingRevenue())
                .operatingExpense(dto.getOperatingExpense())
                .netProfit(dto.getNetProfit())
                .investmentImpairmentLoss(dto.getInvestmentImpairmentLoss())
                .managementFee(dto.getManagementFee())
                .performanceFee(dto.getPerformanceFee())
                .custodyManagementFee(dto.getCustodyManagementFee())
                .auditFee(dto.getAuditFee())
                .miscExpense(dto.getMiscExpense())
                .build();

        return fundFinancialRepository.save(fundFinancial);
    }

    /**
     * ✅ 특정 조합(FundId)의 재무 리스트 조회 함수
     */
    public List<RFundFinancialDTO> getFundFinancialsByFundId(String fundId) {
        List<FundFinancial> fundFinancials = fundFinancialRepository.findByFund_FundId(fundId);

        return fundFinancials.stream()
                .map(fundFinancial -> new RFundFinancialDTO(
                        fundFinancial.getFinancialYear(),
                        fundFinancial.getVentureAssets(),
                        fundFinancial.getOtherAssets(),
                        fundFinancial.getTotalAssets(),
                        fundFinancial.getCurrentLiabilities(),
                        fundFinancial.getOtherLiabilities(),
                        fundFinancial.getTotalLiabilities(),
                        fundFinancial.getCapital(),
                        fundFinancial.getRetainedEarnings(),
                        fundFinancial.getTotalCapital(),
                        fundFinancial.getOperatingRevenue(),
                        fundFinancial.getOperatingExpense(),
                        fundFinancial.getNetProfit(),
                        fundFinancial.getInvestmentImpairmentLoss(),
                        fundFinancial.getManagementFee(),
                        fundFinancial.getPerformanceFee(),
                        fundFinancial.getCustodyManagementFee(),
                        fundFinancial.getAuditFee(),
                        fundFinancial.getMiscExpense()
                ))
                .collect(Collectors.toList());
    }
}
