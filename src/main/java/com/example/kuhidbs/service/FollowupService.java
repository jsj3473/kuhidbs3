package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CFolDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.entity.Followup;
import com.example.kuhidbs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowupService {

    @Autowired
    private FollowupRepository followupRepository;

    @Autowired
    private CmpRepository companyRepository;

    /**
     * 후속 투자 정보를 저장.
     *
     * @param followupDto 후속 투자 정보가 담긴 DTO 객체
     * @return 저장된 Followup 객체
     */
    public Followup saveFollowup(CFolDTO followupDto) {
        Followup followup = toEntity(followupDto);
        return followupRepository.save(followup);
    }

    /**
     * CFolDTO를 Followup 엔터티로 변환하는 메서드.
     *
     * @param dto 변환할 DTO 객체
     * @return 변환된 Followup 엔터티
     */
    private Followup toEntity(CFolDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + dto.getCompanyId()));

        return Followup.builder()
                .company(company) // ManyToOne 관계로 설정
                .followupStartDate(dto.getFollowupStartDate())
                .followupCompanyName(dto.getFollowupCompanyName())
                .followupProduct(dto.getFollowupProduct())
                .followupSumPrice(dto.getFollowupSumPrice())
                .followupShareCount(dto.getFollowupShareCount())
                .followupUnitPrice(dto.getFollowupUnitPrice())
                .followupEquityRate(dto.getFollowupEquityRate())
                .followupInvestmentValue(dto.getFollowupInvestmentValue())
                .followupStep(dto.getFollowupStep())
                .build();
    }

}
