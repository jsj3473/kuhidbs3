package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.후속투자.CFolDTO;
import com.example.kuhidbs.dto.company.후속투자.RFolDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Followup;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.FollowupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowupService {

    @Autowired
    private FollowupRepository followupRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

    @Transactional(readOnly = true)
    public Long getCurrentCompanyValue(String companyId) {
        return followupRepository.findTopByCompany_CompanyIdOrderByFollowupStartDateDesc(companyId)
                .map(Followup::getFollowupInvestmentValue)
                .orElse(null); // 최신 투자 밸류가 없으면 null 반환
    }

    // 회사 ID를 기준으로 후속 투자 정보 조회
    public List<RFolDTO> getFollowupByCompanyId(String companyId) {
        List<Followup> followups = followupRepository.findByCompany_CompanyId(companyId);

        // Entity -> DTO 변환
        return followups.stream()
                .map(followup -> RFolDTO.builder()
                        .followupStartDate(followup.getFollowupStartDate())
                        .followupCompanyName(followup.getFollowupCompanyName())
                        .followupProduct(followup.getFollowupProduct())
                        .followupSumPrice(followup.getFollowupSumPrice())
                        .followupShareCount(followup.getFollowupShareCount())
                        .followupUnitPrice(followup.getFollowupUnitPrice())
                        .followupEquityRate(followup.getFollowupEquityRate())
                        .followupInvestmentValue(followup.getFollowupInvestmentValue())
                        .followupStep(followup.getFollowupStep())
                        .build())
                .collect(Collectors.toList());
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
