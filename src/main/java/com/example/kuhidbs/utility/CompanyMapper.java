package com.example.kuhidbs.utility;

import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.entity.Company;

import java.util.HashSet;

public class CompanyMapper {

    private CompanyMapper() {
        // 객체 생성 방지
    }

    public static Company toEntity(CreateCompanyDTO dto) {
        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        company.setAddress(dto.getAddress());
        company.setBusinessId(dto.getBusinessId());
        company.setCorporateId(dto.getCorporateId());
        company.setCeoName(dto.getCeoName());
        company.setIndustryCode(dto.getIndustryCode());
        company.setEstablishedDate(dto.getEstablishedDate());
        company.setIndustry(dto.getIndustry());
        company.setBusinessItem(dto.getBusinessItem());
        company.setFounderCarrerType(Company.FounderCarrerType.valueOf(dto.getFounderCarrerType()));
        company.setFounderUnivType(Company.FounderUnivType.valueOf(dto.getFounderUnivType()));
        company.setCertifications(new HashSet<>(dto.getCertifications()));
        company.setCapital(dto.getCapital());
        company.setFaceValue(dto.getFaceValue());
        company.setInvestmentStage(dto.getInvestmentStage());
        company.setEnterpriseValue(dto.getEnterpriseValue());
        company.setFourInsuranceMembers(dto.getFourInsuranceMembers());
        return company;
    }
}
