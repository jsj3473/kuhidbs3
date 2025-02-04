package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.*;
import com.example.kuhidbs.entity.*;
import com.example.kuhidbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CmpService cmpService;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private FollowupService followupService;

    @Autowired
    private ShrupService shrupService;

    @Autowired
    private RecoveryService recoveryService;

    @Autowired
    private CombineService combineService;

    @Autowired
    private ShareholderService shareholderService;

    @Autowired
    private ManageService manageService;

    @Autowired
    private BonusService bonusService;

    @PostMapping("/createCompany")
    public ResponseEntity<Void> createCompany(@RequestBody CCmpInfDTO CCmpInfDTO) {

        // 1. 회사 정보 저장 (Company 테이블)
        cmpService.saveCompany(CCmpInfDTO);

        // 2. 재무 정보 저장 (Financial 테이블)
        financialService.saveFinancial(CCmpInfDTO);

        // 3. 관리자 정보 저장 (발굴자, 심사자, 사후관리자) (Manager 테이블)
        reviewerService.saveReviewers(CCmpInfDTO);

        // 4. 피투자기업 실무자 정보 저장 (Client 테이블)
        clientService.saveClient(CCmpInfDTO);

        // 요청이 성공적으로 처리된 경우 HTTP 201 응답 반환
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/createInvestment")
    public ResponseEntity<?> createInvestment(@RequestBody CIvtDTO cIvtDTO) {
        // InvestmentDto를 받아 InvestmentService에 전달
        Investment savedInvestment = investmentService.saveInvestment(cIvtDTO);
        return ResponseEntity.ok(savedInvestment); // 저장된 Investment 반환
    }

    /**
     * 후속 투자 정보를 생성하는 API 엔드포인트.
     *
     * @param followupDto 후속 투자 정보가 담긴 DTO 객체
     * @return 저장된 Followup 객체
     */
    @PostMapping("/createFollowup")
    public ResponseEntity<Followup> createFollowup(@RequestBody CFolDTO followupDto) {
        Followup savedFollowup = followupService.saveFollowup(followupDto);
        return ResponseEntity.ok(savedFollowup);
    }


    /**
     * 감액/복원 데이터 생성
     */
    @PostMapping("/createShrup")
    public ResponseEntity<ShareUpdate> createShrup(@RequestBody CShrupDTO shrupDTO) {
        ShareUpdate createdShrup = shrupService.saveShrup(shrupDTO);
        return ResponseEntity.ok(createdShrup);
    }
    //회수 생성
    @PostMapping("/createStcup")
    public ResponseEntity<Recovery> createRecovery(@RequestBody CStcupDTO stcupDTO) {
        Recovery savedRecovery = recoveryService.saveRecovery(stcupDTO);
        return ResponseEntity.ok(savedRecovery);
    }


    // 동반투자 생성
    @PostMapping("/createCombine")
    public ResponseEntity<Combine> createCombine(@RequestBody CComDTO combineDTO) {
        Combine savedCombine = combineService.saveCombine(combineDTO);
        return ResponseEntity.ok(savedCombine);
    }

    //주주생성
    @PostMapping("/createShareholder")
    public ResponseEntity<Shareholder> createShareholder(@RequestBody CShrDTO shareholderDTO) {
        Shareholder savedShareholder = shareholderService.createShareholder(shareholderDTO);
        return ResponseEntity.ok(savedShareholder);
    }

    //재무제표생성
    @PostMapping("/createFinancial")
    public ResponseEntity<Financial> createFinancialStatement(@RequestBody CFncDTO dto) {
        Financial createdFinancial = financialService.saveFinancialForCFncDTO(dto);
        return ResponseEntity.ok(createdFinancial);
    }

    // 사후관리 정보 생성 API
    @PostMapping("/createManage")
    public ResponseEntity<Manage> createManage(@RequestBody CMngDTO dto) {
        Manage createdManage =  manageService.createManage(dto);
        return ResponseEntity.ok(createdManage);
    }

    // Reviewer 데이터 생성 API
    @PostMapping("/createReviewer")
    public ResponseEntity<Reviewer> createReviewer(@RequestBody CRwrDTO dto) {
        Reviewer createdReviewer = reviewerService.createReviewerForCRwrDTO(dto);
        return ResponseEntity.ok(createdReviewer);
    }

    // 무상증자 생성 API
    @PostMapping("/createBouns")
    public ResponseEntity<Void> createBonus(@RequestBody CBonusDTO dto) {
        bonusService.createBonus(dto);
        return ResponseEntity.ok().build();
    }


    // 회사 기본 정보 조회 API
    @GetMapping("/{companyId}")
    public ResponseEntity<RCmpInfDTO> getCompanyInfo(@PathVariable String companyId) {
        RCmpInfDTO companyInfo = cmpService.getCompanyInfo(companyId);
        return ResponseEntity.ok(companyInfo);
    }
}
