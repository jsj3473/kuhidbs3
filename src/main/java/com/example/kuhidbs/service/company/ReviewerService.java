package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.cmpinf.CCmpInfDTO;
import com.example.kuhidbs.dto.company.reviewer.CRwrDTO;
import com.example.kuhidbs.dto.company.reviewer.RRwrDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Reviewer;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.ReviewerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public void saveReviewers(CCmpInfDTO CCmpInfDTO) {

        // Company 객체 조회
        Company company = companyRepository.findById(CCmpInfDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + CCmpInfDTO.getCompanyId()));

        // 발굴자 저장
        Reviewer manager1 = Reviewer.builder()// Example ID generation
                .company(company)
                .managerType("발굴자")
                .manager(CCmpInfDTO.getManager1())
                .changeReason("최초등록")
                .build();
        reviewerRepository.save(manager1);

        // 심사자 저장
        Reviewer manager2 = Reviewer.builder()// Example ID generation
                .company(company)
                .managerType("심사자")
                .manager(CCmpInfDTO.getManager2())
                .changeReason("최초등록")
                .build();
        reviewerRepository.save(manager2);

        // 사후관리자 저장
        Reviewer manager3 = Reviewer.builder() // Example ID generation
                .company(company)
                .managerType("사후관리자")
                .manager(CCmpInfDTO.getManager3())
                .changeReason("최초등록")
                .build();
        reviewerRepository.save(manager3);
    }

    // Reviewer 데이터 생성
    @Transactional
    public Reviewer createReviewerForCRwrDTO(CRwrDTO dto) {
        // 회사 조회 (외래키 확인)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // DTO → Entity 변환 후 저장
        Reviewer reviewer = Reviewer.builder()
                .company(company)
                .managerType(dto.getManagerType())
                .manager(dto.getManager())
                .changeReason(dto.getChangeReason())
                .build();

        return reviewerRepository.save(reviewer);
    }


    @Transactional(readOnly = true)
    public String getLatestManagerByType(String companyId, String managerType) {
        return reviewerRepository.findTopByCompany_CompanyIdAndManagerTypeOrderByChangeIdDesc(companyId, managerType)
                .map(Reviewer::getManager)
                .orElse(null); // 값이 없으면 null 반환
    }

    @Transactional(readOnly = true)
    public List<RRwrDTO> getReviewersByCompanyId(String companyId) {
        // companyId에 해당하는 모든 Reviewer 조회
        List<Reviewer> reviewers = reviewerRepository.findByCompany_CompanyId(companyId);

        // Entity → DTO 변환 후 리스트 반환
        return reviewers.stream()
                .map(reviewer -> new RRwrDTO(
                        reviewer.getManagerType(),
                        reviewer.getManager(),
                        reviewer.getChangeReason()
                ))
                .collect(Collectors.toList());
    }
}
