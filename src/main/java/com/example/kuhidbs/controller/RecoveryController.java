package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.recovery.CreateRecoveryDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.entity.Recovery;
import com.example.kuhidbs.service.CompanyService;
import com.example.kuhidbs.service.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recoveries")
public class RecoveryController {

    private final RecoveryService recoveryService;
    private final CompanyService companyService;

    @Autowired
    public RecoveryController(RecoveryService recoveryService, CompanyService companyService) {
        this.recoveryService = recoveryService;
        this.companyService = companyService;
    }

    /**
     * 회수 정보 생성
     *
     * @param createRecoveryDTO 회수 정보 데이터
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> createRecovery(@RequestBody CreateRecoveryDTO createRecoveryDTO) {
        // Company 엔터티 조회
        Company company = companyService.getCompanyById(createRecoveryDTO.getCompanyId());

        // DTO를 엔터티로 변환
        Recovery recovery = createRecoveryDTO.toEntity(company);

        // 서비스 호출로 데이터 저장
        recoveryService.createRecovery(recovery);

        return ResponseEntity.ok("Recovery created successfully.");
    }
}
