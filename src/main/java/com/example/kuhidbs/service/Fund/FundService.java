package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.CFundDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundService {

    private final FundRepository fundRepository;

    // 펀드 생성
    public Fund createFund(CFundDTO dto) {
        Fund fund = Fund.builder()
                .fundName(dto.getFundName())
                .fundNameDetail(dto.getFundNameDetail())
                .establishmentDate(dto.getEstablishmentDate())
                .durationStartDate(dto.getDurationStartDate())
                .durationEndDate(dto.getDurationEndDate())
                .investmentStartDate(dto.getInvestmentStartDate())
                .investmentEndDate(dto.getInvestmentEndDate())
                .committedTotalPrice(dto.getCommittedTotalPrice())
                .paymentType(dto.getPaymentType())
                .fundManager(dto.getFundManager())
                .fundOrganizationType(dto.getFundOrganizationType())
                .tipsType(dto.getTipsType())
                .fundMainRequirement(dto.getFundMainRequirement())
                .targetReturnRate(dto.getTargetReturnRate())
                .performanceFeeRate(dto.getPerformanceFeeRate())
                .managementFee1(dto.getManagementFee1())
                .managementFee2(dto.getManagementFee2())
                .incentive1(dto.getIncentive1())
                .incentive2(dto.getIncentive2())
                .incentive3(dto.getIncentive3())
                .incentive4(dto.getIncentive4())
                .priorLoss1(dto.getPriorLoss1())
                .priorLoss2(dto.getPriorLoss2())
                .dutyRate1(dto.getDutyRate1())
                .dutyRate2(dto.getDutyRate2())
                .dutyRate3(dto.getDutyRate3())
                .dutyRate4(dto.getDutyRate4())
                .dutyRate5(dto.getDutyRate5())
                .dutyRate6(dto.getDutyRate6())
                .dutyRate7(dto.getDutyRate7())
                .dutyRate8(dto.getDutyRate8())
                .build();

        return fundRepository.save(fund);
    }
}
