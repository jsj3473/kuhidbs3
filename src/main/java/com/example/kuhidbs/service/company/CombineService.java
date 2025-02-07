package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.동반.CComDTO;
import com.example.kuhidbs.entity.company.Combine;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.company.CombineRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import org.springframework.stereotype.Service;

@Service
public class CombineService {

    private final CombineRepository combineRepository;
    private final InvestmentRepository investmentRepository;

    public CombineService(CombineRepository combineRepository, InvestmentRepository investmentRepository) {
        this.combineRepository = combineRepository;
        this.investmentRepository = investmentRepository;
    }

    // 동반투자 저장
    public Combine saveCombine(CComDTO combineDTO) {
        Investment investment = investmentRepository.findById(combineDTO.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID"));

        Combine combine = Combine.builder()
                .investment(investment)
                .investmentCompanyName(combineDTO.getInvestmentCompanyName())
                .investmentStartDate(combineDTO.getInvestmentStartDate())
                .investmentUnitPrice(combineDTO.getInvestmentUnitPrice())
                .investmentSumPrice(combineDTO.getInvestmentSumPrice())
                .investmentProduct(combineDTO.getInvestmentProduct())
                .equityRate(combineDTO.getEquityRate())
                .comment(combineDTO.getComment())
                .investmentStep(combineDTO.getInvestmentStep())
                .shareCount(combineDTO.getShareCount())
                .build();

        return combineRepository.save(combine);
    }

}
