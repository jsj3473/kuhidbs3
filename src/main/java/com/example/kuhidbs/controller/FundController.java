package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.entity.Fund.*;
import com.example.kuhidbs.repository.Fund.FundFinancialRepository;
import com.example.kuhidbs.service.Fund.*;
import com.example.kuhidbs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funds")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;
    private final AuditService auditService;
    private final StaffService staffService;
    private final FundFinancialService fundFinancialService;
    private final FundMemService fundMemService;

    // 펀드 생성 API
    @PostMapping("/createFund")
    public ResponseEntity<Fund> createFund(@ModelAttribute CFundDTO dto) {
        System.out.println(dto.getFundId());
        fundService.createFund(dto);
        auditService.createAuditByFund(dto);
        return ResponseEntity.ok().build();
    }

    // 회계감사 정보 추가
    @PostMapping("/createAudit")
    public ResponseEntity<Audit> createAudit(@RequestBody CAuditDTO dto) {
        Audit newAudit = auditService.createAudit(dto);
        return ResponseEntity.ok(newAudit);
    }

    // 운용인력 정보 추가
    @PostMapping("/createStaff")
    public ResponseEntity<Staff> createStaff(@RequestBody CStaffDTO dto) {
        Staff newStaff = staffService.createStaff(dto);
        fundService.updateCurrentStaff(dto.getFundId(), dto.getCurrentStaff());
        return ResponseEntity.ok(newStaff);
    }

    /**
     * ✅ 재무 정보 생성 API
     */
    @PostMapping("/createFundFinancial")
    public FundFinancial createFundFinancial(@RequestBody CFundFinancialDTO dto) {
        return fundFinancialService.createFundFinancial(dto);
    }

    // 특정 fundId에 해당하는 모든 Audit 데이터 조회
    @GetMapping("showAuditsByFund/{fundId}")
    public ResponseEntity<List<RAuditDTO>> getAuditsByFundId(@PathVariable String fundId) {
        List<RAuditDTO> auditList = auditService.getAuditsByFundId(fundId);
        return ResponseEntity.ok(auditList);
    }

    // 특정 fundId에 해당하는 모든 운용 인력 변경 내역 조회
    @GetMapping("showStaffsByFund/{fundId}")
    public ResponseEntity<List<RStaffDTO>> getStaffChangesByFundId(@PathVariable String fundId) {
        List<RStaffDTO> staffChanges = staffService.getStaffChangesByFundId(fundId);
        return ResponseEntity.ok(staffChanges);
    }

    /**
     * ✅ 특정 조합(FundId)의 재무 정보 리스트 조회 API
     */
    @GetMapping("/showFundFinancialsByFund/{fundId}")
    public List<RFundFinancialDTO> getFundFinancials(@PathVariable String fundId) {
        return fundFinancialService.getFundFinancialsByFundId(fundId);
    }


    /**
     * 조합원 목록을 받아 저장하는 API
     */
    @PostMapping("/createFundMem")
    public ResponseEntity<List<FundMem>> createFundMems(@RequestBody List<CFundMemDTO> dtos) {
        List<FundMem> savedFundMems = fundMemService.saveAll(dtos);
        return ResponseEntity.ok(savedFundMems);
    }
}
