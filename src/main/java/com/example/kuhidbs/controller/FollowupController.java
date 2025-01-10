package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.followup.CreateFollowupDTO;
import com.example.kuhidbs.entity.Followup;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.service.FollowupService;
import com.example.kuhidbs.repository.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/followups")
public class FollowupController {

    private final FollowupService followupService;
    private final CompanyRepository companyRepository;

    @Autowired
    public FollowupController(FollowupService followupService, CompanyRepository companyRepository) {
        this.followupService = followupService;
        this.companyRepository = companyRepository;
    }

    /**
     * 후속 투자 생성
     * @param createFollowupDTO 후속 투자 데이터
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> createFollowup(@RequestBody CreateFollowupDTO createFollowupDTO) {
        // Company 엔티티 조회
        Company company = companyRepository.findById(createFollowupDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + createFollowupDTO.getCompanyId()));

        // DTO를 엔티티로 변환
        Followup followup = createFollowupDTO.toEntity(company);

        // 서비스로 전달
        followupService.createFollowup(followup);

        return ResponseEntity.ok("Followup created successfully.");
    }
}
