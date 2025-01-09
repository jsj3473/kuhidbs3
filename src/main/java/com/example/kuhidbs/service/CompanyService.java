package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // Get all companies
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
