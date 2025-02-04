package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CShrDTO;
import com.example.kuhidbs.entity.*;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.*;
import com.example.kuhidbs.repository.ShareholderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareholderService {

    private final ShareholderRepository shareholderRepository;
    private final CmpRepository cmpRepository;

    public ShareholderService(ShareholderRepository shareholderRepository, CmpRepository cmpRepository) {
        this.shareholderRepository = shareholderRepository;
        this.cmpRepository = cmpRepository;
    }

    public Shareholder createShareholder(CShrDTO shareholderDTO) {
        Optional<Company> company = cmpRepository.findById(shareholderDTO.getCompanyId());

        if (company.isEmpty()) {
            throw new IllegalArgumentException("Company not found with ID: " + shareholderDTO.getCompanyId());
        }

        Shareholder shareholder = Shareholder.builder()
                .company(company.get())
                .shareholderName1(shareholderDTO.getShareholderName1())
                .shareholderCount1(shareholderDTO.getShareholderCount1())
                .shareholderName2(shareholderDTO.getShareholderName2())
                .shareholderCount2(shareholderDTO.getShareholderCount2())
                .shareholderName3(shareholderDTO.getShareholderName3())
                .shareholderCount3(shareholderDTO.getShareholderCount3())
                .shareholderName4(shareholderDTO.getShareholderName4())
                .shareholderCount4(shareholderDTO.getShareholderCount4())
                .shareholderName5(shareholderDTO.getShareholderName5())
                .shareholderCount5(shareholderDTO.getShareholderCount5())
                .shareholderName6(shareholderDTO.getShareholderName6())
                .shareholderCount6(shareholderDTO.getShareholderCount6())
                .shareholderName7(shareholderDTO.getShareholderName7())
                .shareholderCount7(shareholderDTO.getShareholderCount7())
                .shareholderName8(shareholderDTO.getShareholderName8())
                .shareholderCount8(shareholderDTO.getShareholderCount8())
                .build();

        return shareholderRepository.save(shareholder);
    }
}
