package com.example.kuhidbs.service.company;

import com.example.kuhidbs.entity.company.Status;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.repository.company.StatusRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final CompanyRepository companyRepository;

    /**
     * 투자 상태 저장 또는 업데이트
     */
    @Transactional
    public Status createStatusFirst(String companyId, String investmentState, String investmentMemo) {
        // 회사 엔티티 조회 (회사 ID 기반)
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않습니다. ID: " + companyId));


        Status newStatus = Status.builder()
                .status(investmentState)
                .additionalInfo(investmentMemo)
                .company(company)
                .build();
        return statusRepository.save(newStatus);

    }
}
