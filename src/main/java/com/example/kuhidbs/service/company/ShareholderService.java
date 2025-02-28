package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.주주명부.CShrDTO;
import com.example.kuhidbs.dto.company.주주명부.RShrDTO;
import com.example.kuhidbs.entity.CompanyAccount;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Shareholder;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.company.ShareholderRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ShareholderService {

    private final ShareholderRepository shareholderRepository;
    private final CompanyRepository companyRepository;
    private final CompanyAccountRepository companyAccountRepository;

    public ShareholderService(ShareholderRepository shareholderRepository,
                              CompanyRepository companyRepository,
                              CompanyAccountRepository companyAccountRepository) {
        this.shareholderRepository = shareholderRepository;
        this.companyRepository = companyRepository;
        this.companyAccountRepository = companyAccountRepository;
    }

    public Shareholder createShareholder(CShrDTO shareholderDTO) {
        Company company = companyRepository.findById(shareholderDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + shareholderDTO.getCompanyId()));

        Shareholder shareholder = Shareholder.builder()
                .company(company)
                .shareholderName1(shareholderDTO.getShareholderName1())
                .shareholderCount1(shareholderDTO.getShareholderCount1())
                .shareholderRate1(shareholderDTO.getShareholderRate1()) // ✅ 추가됨

                .shareholderName2(shareholderDTO.getShareholderName2())
                .shareholderCount2(shareholderDTO.getShareholderCount2())
                .shareholderRate2(shareholderDTO.getShareholderRate2()) // ✅ 추가됨

                .shareholderName3(shareholderDTO.getShareholderName3())
                .shareholderCount3(shareholderDTO.getShareholderCount3())
                .shareholderRate3(shareholderDTO.getShareholderRate3()) // ✅ 추가됨

                .shareholderName4(shareholderDTO.getShareholderName4())
                .shareholderCount4(shareholderDTO.getShareholderCount4())
                .shareholderRate4(shareholderDTO.getShareholderRate4()) // ✅ 추가됨

                .shareholderName5(shareholderDTO.getShareholderName5())
                .shareholderCount5(shareholderDTO.getShareholderCount5())
                .shareholderRate5(shareholderDTO.getShareholderRate5()) // ✅ 추가됨

                .shareholderName6(shareholderDTO.getShareholderName6())
                .shareholderCount6(shareholderDTO.getShareholderCount6())
                .shareholderRate6(shareholderDTO.getShareholderRate6()) // ✅ 추가됨

                .shareholderName7(shareholderDTO.getShareholderName7())
                .shareholderCount7(shareholderDTO.getShareholderCount7())
                .shareholderRate7(shareholderDTO.getShareholderRate7()) // ✅ 추가됨

                .shareholderName8(shareholderDTO.getShareholderName8())
                .shareholderCount8(shareholderDTO.getShareholderCount8())
                .shareholderRate8(shareholderDTO.getShareholderRate8()) // ✅ 추가됨

                .totalShareCount(shareholderDTO.getTotalShareCount()) // ✅ 총 발행 주식 수 추가
                .build();
        // ✅ 고려대 지분율 가져오기
        BigDecimal kuhOwnershipPercentage = findKuhOwnership(shareholderDTO);

        // ✅ CompanyAccount 업데이트
        updateCompanyAccount(company, shareholderDTO.getTotalShareCount(), kuhOwnershipPercentage);

        return shareholderRepository.save(shareholder);
    }

    /**
     * "고려대" 주주의 지분율을 찾아 반환
     */
    private BigDecimal findKuhOwnership(CShrDTO shareholderDTO) {
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName1())) {
            return shareholderDTO.getShareholderRate1();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName2())) {
            return shareholderDTO.getShareholderRate2();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName3())) {
            return shareholderDTO.getShareholderRate3();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName4())) {
            return shareholderDTO.getShareholderRate4();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName5())) {
            return shareholderDTO.getShareholderRate5();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName6())) {
            return shareholderDTO.getShareholderRate6();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName7())) {
            return shareholderDTO.getShareholderRate7();
        }
        if ("고려대기술지주".equals(shareholderDTO.getShareholderName8())) {
            return shareholderDTO.getShareholderRate8();
        }
        return BigDecimal.ZERO; // 고려대가 없을 경우 0 반환
    }

    /**
     * CompanyAccount의 totalSharesIssued 및 kuhOwnershipPercentage 업데이트
     */
    private void updateCompanyAccount(Company company, Long totalShares, BigDecimal kuhOwnershipPercentage) {
        Optional<CompanyAccount> companyAccountOptional = companyAccountRepository.findByCompanyId(company.getCompanyId());

        if (companyAccountOptional.isPresent()) {
            // ✅ 기존 CompanyAccount 업데이트
            CompanyAccount companyAccount = companyAccountOptional.get();
            companyAccount.setTotalSharesIssued(totalShares);
            companyAccount.setKuhOwnershipPercentage(kuhOwnershipPercentage);
            companyAccountRepository.save(companyAccount);
        } else {
            // ✅ CompanyAccount가 없으면 새로 생성
            CompanyAccount newCompanyAccount = CompanyAccount.builder()
                    .company(company)
                    .totalSharesIssued(totalShares)
                    .kuhOwnershipPercentage(kuhOwnershipPercentage)
                    .build();
            companyAccountRepository.save(newCompanyAccount);
        }
    }

    @Transactional(readOnly = true)
    public RShrDTO getShareholderByCompanyId(String companyId) {
        Optional<Shareholder> shareholderOpt = shareholderRepository.findTopByCompany_CompanyIdOrderByShareholderIdDesc(companyId);

        return shareholderOpt.map(shareholder -> RShrDTO.builder()
                .shareholderName1(shareholder.getShareholderName1())
                .shareholderCount1(shareholder.getShareholderCount1())
                .shareholderRate1(shareholder.getShareholderRate1())
                .shareholderName2(shareholder.getShareholderName2())
                .shareholderCount2(shareholder.getShareholderCount2())
                .shareholderRate2(shareholder.getShareholderRate2())
                .shareholderName3(shareholder.getShareholderName3())
                .shareholderCount3(shareholder.getShareholderCount3())
                .shareholderRate3(shareholder.getShareholderRate3())
                .shareholderName4(shareholder.getShareholderName4())
                .shareholderCount4(shareholder.getShareholderCount4())
                .shareholderRate4(shareholder.getShareholderRate4())
                .shareholderName5(shareholder.getShareholderName5())
                .shareholderCount5(shareholder.getShareholderCount5())
                .shareholderRate5(shareholder.getShareholderRate5())
                .shareholderName6(shareholder.getShareholderName6())
                .shareholderCount6(shareholder.getShareholderCount6())
                .shareholderRate6(shareholder.getShareholderRate6())
                .shareholderName7(shareholder.getShareholderName7())
                .shareholderCount7(shareholder.getShareholderCount7())
                .shareholderRate7(shareholder.getShareholderRate7())
                .shareholderName8(shareholder.getShareholderName8())
                .shareholderCount8(shareholder.getShareholderCount8())
                .shareholderRate8(shareholder.getShareholderRate8())
                .totalShareCount(shareholder.getTotalShareCount())
                .build()).orElse(null);
    }
}
