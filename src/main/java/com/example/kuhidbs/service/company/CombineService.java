package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.동반.CComDTO;
import com.example.kuhidbs.entity.company.Combine;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.company.CombineRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CombineService {

    private final CombineRepository combineRepository;
    private final InvestmentRepository investmentRepository;
    private final CompanyRepository companyRepository;



    // 동반투자 저장
    public Combine saveCombine(CComDTO combineDTO) {
        Investment investment = investmentRepository.findById(combineDTO.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID"));

        Company company = companyRepository.findById(combineDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + combineDTO.getInvestmentId()));

        Combine combine = Combine.builder()
                .investment(investment)
                .company(company)
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
