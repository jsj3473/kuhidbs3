package com.example.kuhidbs.service;

import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // 회사 생성 함수
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }


    /**
     * 회사 ID로 회사 정보 조회
     * @param companyId 회사 ID
     * @return Company 엔터티
     * @throws IllegalArgumentException 회사 ID에 해당하는 데이터가 없을 경우 예외 발생
     */
    public Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));
    }
}
