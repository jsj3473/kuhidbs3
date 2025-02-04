package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CComDTO;
import com.example.kuhidbs.entity.Combine;
import com.example.kuhidbs.entity.Investment;
import com.example.kuhidbs.repository.CombineRepository;
import com.example.kuhidbs.repository.InvestmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
