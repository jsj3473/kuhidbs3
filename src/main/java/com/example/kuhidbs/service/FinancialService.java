package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.financial.CreateFinancialDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.entity.Financial;
import com.example.kuhidbs.repository.CompanyRepository;
import com.example.kuhidbs.repository.FinancialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialService {

    @Autowired
    private FinancialRepository financialRepository;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 재무 데이터 생성 서비스
     * @param createFinancialDTO 재무 데이터 DTO
     */
    public void createFinancial(CreateFinancialDTO createFinancialDTO) {
        // 회사 ID로 Company 조회
        Company company = companyRepository.findById(createFinancialDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + createFinancialDTO.getCompanyId()));

        // DTO → Entity 변환 및 저장
        Financial financial = createFinancialDTO.toEntity(company);
        financialRepository.save(financial);
    }
}
