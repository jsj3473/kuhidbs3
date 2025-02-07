package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.Fund.CAuditDTO;
import com.example.kuhidbs.dto.Fund.CFundDTO;
import com.example.kuhidbs.dto.Fund.CStaffDTO;
import com.example.kuhidbs.entity.Audit;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Staff;
import com.example.kuhidbs.service.AuditService;
import com.example.kuhidbs.service.Fund.FundService;
import com.example.kuhidbs.service.Fund.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funds")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;
    private final AuditService auditService;
    private final StaffService staffService;

    // 펀드 생성 API
    @PostMapping("/createFund")
    public ResponseEntity<Fund> createFund(@RequestBody CFundDTO dto) {
        Fund newFund = fundService.createFund(dto);
        return ResponseEntity.ok(newFund);
    }

    // 회계감사 정보 추가
    @PostMapping("/createAudit")
    public ResponseEntity<Audit> createAudit(@RequestBody CAuditDTO dto) {
        Audit newAudit = auditService.createAudit(dto);
        return ResponseEntity.ok(newAudit);
    }

    // 운용인력 정보 추가
    @PostMapping("/create")
    public ResponseEntity<Staff> createStaff(@RequestBody CStaffDTO dto) {
        Staff newStaff = staffService.createStaff(dto);
        return ResponseEntity.ok(newStaff);
    }
}
