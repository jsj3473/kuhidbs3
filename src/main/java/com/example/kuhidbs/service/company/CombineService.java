package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.동반.CComDTO;
import com.example.kuhidbs.dto.company.동반.RComDTO;
import com.example.kuhidbs.entity.company.Combine;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.company.CombineRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 특정 companyId로 RComDTO 조회
    public List<RComDTO> getRComByCompanyId(String companyId) {
        List<Combine> rComEntities = combineRepository.findByCompany_CompanyId(companyId);
        return rComEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Entity → DTO 변환 메서드
    private RComDTO convertToDTO(Combine entity) {
        return new RComDTO(
                entity.getCompany().getCompanyId(),
                entity.getInvestmentCompanyName(),
                entity.getInvestmentStartDate(),
                entity.getInvestmentUnitPrice(),
                entity.getInvestmentSumPrice(),
                entity.getInvestmentProduct(),
                entity.getEquityRate(),
                entity.getComment(),
                entity.getInvestmentStep(),
                entity.getShareCount()
        );
    }
}
