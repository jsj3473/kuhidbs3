package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.*;
import com.example.kuhidbs.entity.*;
import com.example.kuhidbs.entity.*;
import com.example.kuhidbs.repository.CmpRepository;
import com.example.kuhidbs.repository.ReviewerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private CmpRepository companyRepository;

    public void saveReviewers(CCmpInfDTO CCmpInfDTO) {

        // Company 객체 조회
        Company company = companyRepository.findById(CCmpInfDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + CCmpInfDTO.getCompanyId()));

        // 발굴자 저장
        Reviewer manager1 = Reviewer.builder()// Example ID generation
                .company(company)
                .managerType("발굴자")
                .manager(CCmpInfDTO.getManager1())
                .build();
        reviewerRepository.save(manager1);

        // 심사자 저장
        Reviewer manager2 = Reviewer.builder()// Example ID generation
                .company(company)
                .managerType("심사자")
                .manager(CCmpInfDTO.getManager2())
                .build();
        reviewerRepository.save(manager2);

        // 사후관리자 저장
        Reviewer manager3 = Reviewer.builder() // Example ID generation
                .company(company)
                .managerType("사후관리자")
                .manager(CCmpInfDTO.getManager3())
                .build();
        reviewerRepository.save(manager3);
    }

    // Reviewer 데이터 생성
    @Transactional
    public void createReviewerForCRwrDTO(CRwrDTO dto) {
        // 회사 조회 (외래키 확인)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // DTO → Entity 변환 후 저장
        Reviewer reviewer = Reviewer.builder()
                .company(company)
                .comment(dto.getComment())
                .managerType(dto.getManagerType())
                .manager(dto.getManager())
                .changeReason(dto.getChangeReason())
                .build();

        reviewerRepository.save(reviewer);
    }
}
