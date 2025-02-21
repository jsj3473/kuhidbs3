package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.후속투자.CFolDTO;
import com.example.kuhidbs.dto.company.후속투자.RFolDTO;
import com.example.kuhidbs.entity.CompanyAccount;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Followup;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.FollowupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowupService {

    @Autowired
    private FollowupRepository followupRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyAccountRepository companyAccountRepository;
    /**
     * 후속 투자 정보를 저장.
     *
     * @param followupDto 후속 투자 정보가 담긴 DTO 객체
     * @return 저장된 Followup 객체
     */
    @Transactional
    public Followup saveFollowup(CFolDTO followupDto) {
        // ✅ CompanyAccount 가져오기 (없으면 예외 발생)
        CompanyAccount companyAccount = companyAccountRepository.findByCompanyId(followupDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("CompanyAccount not found with ID: " + followupDto.getCompanyId()));

        // ✅ 시가총액(Market Cap) 계산 (BigDecimal 사용하여 정밀도 유지)
        Long totalSharesIssued = Optional.ofNullable(companyAccount.getTotalSharesIssued()).orElse(0L);
        BigDecimal followupUnitPrice = BigDecimal.valueOf(followupDto.getFollowupUnitPrice());
        BigDecimal marketCap = followupUnitPrice.multiply(BigDecimal.valueOf(totalSharesIssued));

        // ✅ 시가총액 업데이트 후 저장
        companyAccount.setMarketCap(marketCap);
        companyAccountRepository.save(companyAccount); // 변경 사항을 DB에 반영

        // ✅ Followup 엔티티 변환 및 저장
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
                        .followupInvestmentValue(followup.getFollowupInvestmentValue())
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
                .followupInvestmentValue(dto.getFollowupInvestmentValue())
                .build();
    }

}
