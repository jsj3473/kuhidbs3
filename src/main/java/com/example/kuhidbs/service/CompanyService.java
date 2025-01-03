package com.example.kuhidbs.service;

import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // 회사 생성 함수
    public Company createCompany(Company company) {
        if (company == null || company.getCompanyName() == null || company.getCompanyName().isEmpty()) {
            throw new IllegalArgumentException("Company name must not be null or empty.");
        }
        return companyRepository.save(company);
    }
}
