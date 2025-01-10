package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.dto.company.ReadCompanyDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.repository.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // 회사 생성 함수
    public Company createCompany(CreateCompanyDTO createCompanyDTO) {
        Company company = createCompanyDTO.toEntity();
        return companyRepository.save(company);
    }

    public Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));
    }


    public List<ReadCompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> {
            ReadCompanyDTO dto = new ReadCompanyDTO();
            dto.setCompanyId(company.getCompanyId());
            dto.setCompanyName(company.getCompanyName());
            dto.setAddress(company.getAddress());
            dto.setBusinessId(company.getBusinessId());
            dto.setCorporateId(company.getCorporateId());
            dto.setCeoName(company.getCeoName());
            dto.setIndustryCode(company.getIndustryCode());
            dto.setEstablishedDate(company.getEstablishedDate());
            dto.setIndustry(company.getIndustry());
            dto.setBusinessItem(company.getBusinessItem());
            dto.setFounderCarrerType(company.getFounderCarrerType());
            dto.setFounderUnivType(company.getFounderUnivType());
            dto.setCertificationType(company.getCertificationType());
            dto.setCapital(company.getCapital());
            dto.setIsEarlyStageStartup(company.getIsEarlyStageStartup());
            dto.setFaceValue(company.getFaceValue());
            dto.setInvestmentStage(company.getInvestmentStage());
            dto.setInvestmentValue(company.getInvestmentValue());
            dto.setInvestmentDate(company.getInvestmentDate());
            dto.setInvestmentFunding(company.getInvestmentFunding());
            dto.setInvestmentMethod(company.getInvestmentMethod());
            dto.setCashInvestmentPrice(company.getCashInvestmentPrice());
            dto.setNonCashInvestmentPrice(company.getNonCashInvestmentPrice());
            dto.setCashInvestmentUnit(company.getCashInvestmentUnit());
            dto.setNonCashInvestmentUnit(company.getNonCashInvestmentUnit());
            dto.setInvestmentProduct(company.getInvestmentProduct());
            dto.setAcquisitionCost(company.getAcquisitionCost());
            dto.setTipsSupport(company.getTipsSupport());
            dto.setIsListed(company.getIsListed());
            dto.setListingMarket(company.getListingMarket());
            dto.setListingDate(company.getListingDate());
            dto.setInvestmentPoint1(company.getInvestmentPoint1());
            dto.setInvestmentPoint2(company.getInvestmentPoint2());
            dto.setInvestmentPoint3(company.getInvestmentPoint3());
            return dto;
        }).collect(Collectors.toList());
    }
}
