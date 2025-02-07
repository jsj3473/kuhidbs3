package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.기본정보.CCmpInfDTO;
import com.example.kuhidbs.dto.company.재무.CFncDTO;
import com.example.kuhidbs.dto.company.재무.RFncDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Financial;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.FinancialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialService {

    @Autowired
    private FinancialRepository financialRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public void saveFinancial(CCmpInfDTO CCmpInfDTO) {

        // Company 객체 조회
        Company company = companyRepository.findById(CCmpInfDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + CCmpInfDTO.getCompanyId()));
        Financial financial = Financial.builder()
                .company(company)
                .financialYear(CCmpInfDTO.getFinancialYear())
                .financialHalf(CCmpInfDTO.getFinancialHalf())
                .revenue(CCmpInfDTO.getRevenue())
                .operatingProfit(CCmpInfDTO.getOperatingProfit())
                .netIncome(CCmpInfDTO.getNetIncome())
                .totalAssets(CCmpInfDTO.getTotalAssets())
                .totalCapital(CCmpInfDTO.getTotalCapital())
                .capital(CCmpInfDTO.getCapital())
                .employeeCount(CCmpInfDTO.getEmployeeCount())
                .totalDebt(CCmpInfDTO.getTotalDebt())
                .build();

        financialRepository.save(financial);
    }

    public Financial saveFinancialForCFncDTO(CFncDTO dto) {

        // Company 객체 조회
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + dto.getCompanyId()));
        Financial financial = Financial.builder()
                .company(company)
                .financialYear(dto.getFinancialYear())
                .financialHalf(dto.getFinancialHalf())
                .revenue(dto.getRevenue())
                .operatingProfit(dto.getOperatingProfit())
                .netIncome(dto.getNetIncome())
                .totalAssets(dto.getTotalAssets())
                .totalCapital(dto.getTotalCapital())
                .capital(dto.getCapital())
                .employeeCount(dto.getEmployeeCount())
                .totalDebt(dto.getTotalDebt())
                .build();

        return financialRepository.save(financial);
    }

    @Transactional(readOnly = true)
    public List<RFncDTO> getRecentFinancialsByCompanyId(String companyId) {
        return financialRepository
                .findTop2ByCompany_CompanyIdOrderByFinancialYearDescFinancialHalfDesc(companyId)
                .stream()
                .map(financial -> RFncDTO.builder()
                        .financialYear(financial.getFinancialYear())
                        .financialHalf(financial.getFinancialHalf())
                        .revenue(financial.getRevenue())
                        .operatingProfit(financial.getOperatingProfit())
                        .netIncome(financial.getNetIncome())
                        .totalAssets(financial.getTotalAssets())
                        .totalCapital(financial.getTotalCapital())
                        .capital(financial.getCapital())
                        .employeeCount(financial.getEmployeeCount())
                        .totalDebt(financial.getTotalDebt())
                        .build())
                .collect(Collectors.toList());
    }
}
