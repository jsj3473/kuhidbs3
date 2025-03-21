package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.entity.Fund.*;
import com.example.kuhidbs.repository.Fund.AuditRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.service.Fund.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funds")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;
    private final AuditService auditService;
    private final StaffService staffService;
    private final FundFinancialService fundFinancialService;
    private final FundMemService fundMemService;
    private final IASService iasService;
    private final DueDiligenceService dueDiligenceService;
    private final FundAchievementService fundAchievementService;
    private final EmploymentService employmentService;
    private final FundRepository fundRepository;
    private final AuditRepository auditRepository;
    private static final Logger logger = LoggerFactory.getLogger(FundController.class);

    // 펀드 생성 API
    @PostMapping("/createFund")
    public ResponseEntity<Void> createFund(@Valid @ModelAttribute CFundDTO dto) {
        logger.info("펀드 생성 요청: {}", dto);

        fundService.createFund(dto);
        auditService.createAuditByFund(dto);

        logger.info("펀드가 성공적으로 생성됨: {}", dto);
        return ResponseEntity.ok().build();
    }

    // 회계감사 정보 추가
    @PostMapping("/createAudit")
    public ResponseEntity<Audit> createAudit(@Valid @RequestBody CAuditDTO dto) {
        logger.info("회계감사 정보 저장 요청: {}", dto);

        Audit newAudit = auditService.createAudit(dto);

        logger.info("회계감사 정보가 성공적으로 저장됨: {}", newAudit);
        return ResponseEntity.ok(newAudit);
    }

    // 운용인력 정보 추가
    @PostMapping("/createStaff")
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody CStaffDTO dto) {
        logger.info("운용인력 정보 저장 요청: {}", dto);

        Staff newStaff = staffService.createStaff(dto);

        logger.info("운용인력 정보가 성공적으로 저장됨: {}", newStaff);
        return ResponseEntity.ok(newStaff);
    }

    // 재무 정보 생성 API
    @PostMapping("/createFundFinancial")
    public ResponseEntity<FundFinancial> createFundFinancial(@Valid @RequestBody CFundFinancialDTO dto) {
        logger.info("재무 정보 저장 요청: {}", dto);

        FundFinancial fundFinancial = fundFinancialService.createFundFinancial(dto);

        logger.info("재무 정보가 성공적으로 저장됨: {}", fundFinancial);
        return ResponseEntity.ok(fundFinancial);
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



    @GetMapping("/showAllFundMem/{fundId}")
    public ResponseEntity<List<RFundMemDTO>> getActiveFundMembersByFundId(@PathVariable("fundId") String fundId) {
        List<RFundMemDTO> fundMembers = fundMemService.getActiveFundMembersByFundId(fundId);
        return ResponseEntity.ok(fundMembers);
    }

    // 특정 펀드에 해당하는 투자자산총괄 데이터를 조회하는 API
    @GetMapping("/showInvestmentAssetSummary/{fundId}")
    public ResponseEntity<List<RIASDTO>> getInvestmentAssetSummariesByFundId(@PathVariable String fundId) {
        List<RIASDTO> summaries = iasService.getInvestmentAssetSummaryByFundId(fundId);
        return ResponseEntity.ok(summaries);
    }

    @PostMapping("/createDueDiligence")
    public ResponseEntity<String> createDueDiligence(@RequestBody CDueDiligenceDTO dto) {
        dueDiligenceService.createDueDiligence(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Due diligence created successfully");
    }

    //펀드id에 걸맞는 투자금 실사현황들 조회하기
    @GetMapping("/showAllDueDils/{fundId}")
    public ResponseEntity<List<RDueDiligenceDTO>> getDueDiligenceByFund(@PathVariable String fundId) {
        List<RDueDiligenceDTO> dtos = dueDiligenceService.getDueDiligenceByFundId(fundId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("info/{fundId}")
    public ResponseEntity<RFundDTO> getFundById(@PathVariable String fundId) {
        RFundDTO fundDTO = fundService.getFundById(fundId);
        List<RFundMemDTO> fundMems = fundMemService.getActiveFundMembersByFundId(fundId);
        RFundAchievementDTO rFundAchievementDTO = fundAchievementService.getFundAchievement(fundId);
        fundDTO.setFundAchievement(rFundAchievementDTO);
        fundDTO.setFundMems(fundMems);
        fundDTO.setAuditorName(auditRepository.findLatestAuditNameByFundId(fundId));
        fundDTO.setChangeDate(auditRepository.findLatestChangeDateByFundId(fundId));
        return ResponseEntity.ok(fundDTO);
    }

    /**
     * 펀드 ID로 Employment 데이터 리스트 조회
     */
    @GetMapping("/showAllEmployment/{fundId}")
    public List<REmploymentDTO> showAllEmployment(@PathVariable String fundId) {
        return employmentService.showAllEmployment(fundId);
    }


    @GetMapping("/searchQueryForFundInHeader")
    public ResponseEntity<List<Object[]>> searchQueryForFundInHeader(@RequestParam String query) {
        List<Object[]> fundIds = fundRepository.searchFunds(query);
        return ResponseEntity.ok(fundIds);
    }

    /**
     * 펀드 정보 수정
     */
    @PutMapping("/updateFund")
    public ResponseEntity<UFundDTO> updateFundInfo(
            @RequestBody UFundDTO updatedFundInfo) {
        logger.info("[PUT] 펀드 정보 수정 - fundId: {}", updatedFundInfo.getFundId());
        UFundDTO updatedInfo = fundService.updateFundInfo(updatedFundInfo);
        fundService.updateFundAchievement(updatedFundInfo);
        return ResponseEntity.ok(updatedInfo);
    }
}
