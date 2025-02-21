package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.사후관리.CMngDTO;
import com.example.kuhidbs.dto.company.투자상태.CStatusDTO;
import com.example.kuhidbs.dto.company.투자상태.RStatusDTO;
import com.example.kuhidbs.entity.company.Status;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.StatusRepository;
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

    @Transactional
    public Status createStatus(CStatusDTO dto) {
        // 회사 엔티티 조회 (회사 ID 기반)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않습니다. ID: " + dto.getCompanyId()));


        Status newStatus = Status.builder()
                .status(dto.getStatus())
                .additionalInfo(dto.getAdditionalInfo())
                .company(company)
                .build();
        return statusRepository.save(newStatus);

    }

    @Transactional
    public void createStatusByManage(CMngDTO dto) {
        // 회사 엔티티 조회 (회사 ID 기반)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않습니다. ID: " + dto.getCompanyId()));


        Status newStatus = Status.builder()
                .status(dto.getEvalGrade())
                .additionalInfo("사후관리에 의해 생성된 투자상태")
                .company(company)
                .build();
        statusRepository.save(newStatus);

    }

    /**
     * 특정 회사의 최신 투자 상태 조회
     */
    @Transactional(readOnly = true)
    public RStatusDTO getStatusByCompanyId(String companyId) {
        Status latestStatus = statusRepository.findTopByCompany_CompanyIdOrderByStatusIdDesc(companyId)
                .orElse(null); // 데이터가 없으면 null 반환

        if (latestStatus == null) {
            // 데이터가 없는 경우, 기본값을 가진 DTO 반환
            return new RStatusDTO(companyId, "N/A", "No investment status available");
        }

        return new RStatusDTO(latestStatus.getCompany().getCompanyId(), latestStatus.getStatus(), latestStatus.getAdditionalInfo());
    }
}
