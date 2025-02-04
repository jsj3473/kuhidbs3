package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CCmpInfDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.CmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmpService {

    @Autowired
    private CmpRepository companyRepository;

    public void saveCompany(CCmpInfDTO CCmpInfDTO) {

        Company company = Company.builder()
                .companyId(CCmpInfDTO.getCompanyId())
                .companyName(CCmpInfDTO.getCompanyName())
                .establishmentDate(CCmpInfDTO.getEstablishmentDate())
                .ceoName(CCmpInfDTO.getCeoName())
                .companyAddress(CCmpInfDTO.getCompanyAddress())
                .businessRegistrationNumber(CCmpInfDTO.getBusinessRegistrationNumber())
                .corporateRegistrationNumber(CCmpInfDTO.getCorporateRegistrationNumber())
                .industryCode(CCmpInfDTO.getIndustryCode())
                .capital(CCmpInfDTO.getCapital())
                .parValue(CCmpInfDTO.getParValue())
                .employeeCount(CCmpInfDTO.getEmployeeCount())
                .startupType(CCmpInfDTO.getStartupType())
                .regionalCompany(CCmpInfDTO.getRegionalCompany())
                .kuhStartup(CCmpInfDTO.getKuhStartup())
                .ventureRecognition(CCmpInfDTO.getVentureRecognition())
                .researchRecognition(CCmpInfDTO.getResearchRecognition())
                .earlyStartupType(CCmpInfDTO.getEarlyStartupType())
                .kuhSubsidiary(CCmpInfDTO.getKuhSubsidiary())
                .investmentSector(CCmpInfDTO.getInvestmentSector())
                .dueDiligence(CCmpInfDTO.getDueDiligence())
                .mainProducts(CCmpInfDTO.getMainProducts())
                .investmentPoint1(CCmpInfDTO.getInvestmentPoint1())
                .investmentPoint2(CCmpInfDTO.getInvestmentPoint2())
                .investmentPoint3(CCmpInfDTO.getInvestmentPoint3())
                .evaluationEmployee(CCmpInfDTO.getEvaluationEmployee())
                .publicTechnologyTransfer(CCmpInfDTO.getPublicTechnologyTransfer())
                .smeStatus(CCmpInfDTO.getSmeStatus())
                .listingDate(CCmpInfDTO.getListingDate())
                .listingStatus(CCmpInfDTO.getListingStatus())
                .companyPostalCode(CCmpInfDTO.getCompanyPostalCode())
                .build();

        companyRepository.save(company);
    }
}
