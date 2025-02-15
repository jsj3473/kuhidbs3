package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.주주명부.CShrDTO;
import com.example.kuhidbs.dto.company.주주명부.RShrDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Shareholder;
import com.example.kuhidbs.repository.company.ShareholderRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShareholderService {

    private final ShareholderRepository shareholderRepository;
    private final CompanyRepository companyRepository;

    public ShareholderService(ShareholderRepository shareholderRepository, CompanyRepository companyRepository) {
        this.shareholderRepository = shareholderRepository;
        this.companyRepository = companyRepository;
    }

    public Shareholder createShareholder(CShrDTO shareholderDTO) {
        Optional<Company> company = companyRepository.findById(shareholderDTO.getCompanyId());

        if (company.isEmpty()) {
            throw new IllegalArgumentException("Company not found with ID: " + shareholderDTO.getCompanyId());
        }

        Shareholder shareholder = Shareholder.builder()
                .company(company.get())
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


        return shareholderRepository.save(shareholder);
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
