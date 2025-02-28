package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.Fund.CDueDiligenceDTO;
import com.example.kuhidbs.dto.company.투자상태.CStatusDTO;
import com.example.kuhidbs.dto.company.kuh투자.*;
import com.example.kuhidbs.dto.company.감액환입.CShrupDTO;
import com.example.kuhidbs.dto.company.기본정보.*;
import com.example.kuhidbs.dto.company.동반.*;
import com.example.kuhidbs.dto.company.무증.CBonusDTO;
import com.example.kuhidbs.dto.company.팁스.발심사.CRwrDTO;
import com.example.kuhidbs.dto.company.사후관리.*;
import com.example.kuhidbs.dto.company.재무.*;
import com.example.kuhidbs.dto.company.주주명부.*;
import com.example.kuhidbs.dto.company.팁스.*;
import com.example.kuhidbs.dto.company.회수.CStcupDTO;
import com.example.kuhidbs.dto.company.후속투자.*;
import com.example.kuhidbs.entity.company.*;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.service.Fund.DueDiligenceService;
import jakarta.validation.Valid;
import com.example.kuhidbs.service.Fund.FundService;
import com.example.kuhidbs.service.company.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

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

    @Autowired
    private TipsService tipsService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private FundService fundService;

    @Autowired
    private CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/createCompany")
    public ResponseEntity<Void> createCompany(@Valid @ModelAttribute CCmpInfDTO CCmpInfDTO) {
        System.out.println(CCmpInfDTO);

        // 1. 회사 정보 저장 (Company 테이블)
        companyService.saveCompany(CCmpInfDTO);

        // 2. 재무 정보 저장 (Financial 테이블)
        financialService.saveFinancial(CCmpInfDTO);

        // 3. 관리자 정보 저장 (발굴자, 심사자, 사후관리자) (Manager 테이블)
        reviewerService.saveReviewers(CCmpInfDTO);

        // 4. 피투자기업 실무자 정보 저장 (Client 테이블)
        clientService.saveClient(CCmpInfDTO);

        logger.info("새로운 회사 정보가 성공적으로 저장되었습니다: {}", CCmpInfDTO);
        // 요청이 성공적으로 처리된 경우 HTTP 201 응답 반환
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/createInvestment")
    public ResponseEntity<Void> createInvestment(@Valid @RequestBody CIvtDTO cIvtDTO) {
        logger.info("투자 정보 저장 요청: {}", cIvtDTO);

        investmentService.saveInvestment(cIvtDTO);
        statusService.createStatusFirst(
                cIvtDTO.getCompanyId(),
                cIvtDTO.getInvestmentState(),
                cIvtDTO.getInvestmentMemo()
        );

        logger.info("투자 정보가 성공적으로 저장됨: {}", cIvtDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/createFollowup")
    public ResponseEntity<Void> createFollowup(@Valid @ModelAttribute CFolDTO followupDto) {
        logger.info("후속투자 정보 저장 요청: {}", followupDto);

        followupService.saveFollowup(followupDto);

        logger.info("후속투자가 성공적으로 저장됨: {}", followupDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/createShrup")
    public ResponseEntity<Void> createShrup(@Valid @RequestBody CShrupDTO shrupDTO) {
        logger.info("감액환입 정보 저장 요청: {}", shrupDTO);

        shrupService.saveShrup(shrupDTO);

        logger.info("감액환입 정보가 성공적으로 저장됨: {}", shrupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/createStcup")
    public ResponseEntity<Void> createRecovery(@Valid @RequestBody CStcupDTO stcupDTO) {
        logger.info("회수 정보 저장 요청: {}", stcupDTO);

        recoveryService.saveRecovery(stcupDTO);

        logger.info("회수 정보가 성공적으로 저장됨: {}", stcupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/createCombine")
    public ResponseEntity<Void> createCombine(@Valid @RequestBody CComDTO combineDTO) {
        logger.info("동반투자 정보 저장 요청: {}", combineDTO);

        combineService.saveCombine(combineDTO);

        logger.info("동반투자 정보가 성공적으로 저장됨: {}", combineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 주주 생성
    @PostMapping("/createShareholder")
    public ResponseEntity<Void> createShareholder(@Valid @RequestBody CShrDTO shareholderDTO) {
        logger.info("주주 정보 저장 요청: {}", shareholderDTO);

        shareholderService.createShareholder(shareholderDTO);

        logger.info("주주 정보가 성공적으로 저장됨: {}", shareholderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 재무제표 생성
    @PostMapping("/createFinancial")
    public ResponseEntity<Void> createFinancialStatement(@Valid @RequestBody CFncDTO dto) {
        logger.info("재무제표 정보 저장 요청: {}", dto);

        financialService.saveFinancialForCFncDTO(dto);

        logger.info("재무제표 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // 무상증자 생성 API
    @PostMapping("/createBonus")
    public ResponseEntity<Void> createBonus(@Valid @RequestBody CBonusDTO dto) {
        logger.info("무상증자 정보 저장 요청: {}", dto);

        bonusService.createBonus(dto);

        logger.info("무상증자 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 사후관리 생성 API
    @PostMapping("/createManage")
    public ResponseEntity<Void> createManage(@Valid @RequestBody CMngDTO dto) {
        logger.info("사후관리 정보 저장 요청: {}", dto);

        manageService.createManage(dto);

        logger.info("사후관리 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 사후관리담당자 생성 API
    @PostMapping("/createReviewer")
    public ResponseEntity<Void> createReviewer(@Valid @RequestBody CRwrDTO dto) {
        logger.info("사후관리담당자 정보 저장 요청: {}", dto);

        reviewerService.createReviewerForCRwrDTO(dto);

        logger.info("사후관리담당자 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TIPS 데이터 생성 API
    @PostMapping("/createTIPS")
    public ResponseEntity<Void> createTIPS(@Valid @RequestBody CTIPSDTO dto) {
        logger.info("TIPS 정보 저장 요청: {}", dto);

        tipsService.createTIPS(dto);

        logger.info("TIPS 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // 투자상태 데이터 생성 API
    @PostMapping("/createStatus")
    public ResponseEntity<Void> createStatus(@Valid @RequestBody CStatusDTO dto) {
        logger.info("투자상태 정보 저장 요청: {}", dto);

        statusService.createStatus(dto);

        logger.info("투자상태 정보가 성공적으로 저장됨: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 팁스 정보 수정 API
    @PutMapping("/updateTIPS")
    public ResponseEntity<String> updateTips(@Valid @RequestBody UTIPSDTO dto) {
        logger.info("TIPS 정보 업데이트 요청: {}", dto);

        tipsService.updateTips(dto);

        logger.info("TIPS 정보가 성공적으로 업데이트됨: {}", dto);
        return ResponseEntity.ok("TIPS 정보가 업데이트되었습니다.");
    }

    // 기업 정보 수정 API
    @PutMapping("/updateCompany")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody UCmpInfDTO companyDTO) {
        logger.info("기업 정보 업데이트 요청: {}", companyDTO);

        Company updatedCompany = companyService.updateCompany(companyDTO);

        logger.info("기업 정보가 성공적으로 업데이트됨: {}", updatedCompany);
        return ResponseEntity.ok(updatedCompany);
    }

    //후속투자 정보 조회 api
    @GetMapping("/followup/{companyId}")
    public List<RFolDTO> getFollowupByCompanyId(@PathVariable String companyId) {
        return followupService.getFollowupByCompanyId(companyId);
    }

    // 회사 기본 정보 조회 API
    @GetMapping("/info/{companyId}")
    public ResponseEntity<RCmpInfDTO> getCompanyInfo(@PathVariable String companyId) {
        RCmpInfDTO companyInfo = companyService.getCompanyInfo(companyId);
        return ResponseEntity.ok(companyInfo);
    }

    // 투자후속관리 회사 기본정보 조회 api
    @GetMapping("/info2/{companyId}")
    public ResponseEntity<RCmpInf2DTO> getCompany2Info(@PathVariable String companyId) {
        RCmpInf2DTO companyInfo = companyService.getCompanyInfo2(companyId);
        return ResponseEntity.ok(companyInfo);
    }

    //헤더에서 회사 검색
    @GetMapping("/searchQueryForCompanyInHeader")
    public ResponseEntity<List<Object[]>> searchQueryForCompanyInHeader(@RequestParam String query) {
        List<Object[]> companyIds = companyRepository.searchCompanies(query);
        return ResponseEntity.ok(companyIds);
    }

    @PostMapping("/updateCompanyName")
    public ResponseEntity<String> updateCompanyName(@RequestBody UCmpInfDTO request) {
        companyService.updateCompanyName(request.getCompanyId(), request.getCompanyName());
        return ResponseEntity.ok("Company name updated successfully");
    }

    @PostMapping("/updateCeoName")
    public ResponseEntity<String> updateCeoName(@RequestBody UCmpInfDTO request) {
        companyService.updateCeoName(request.getCompanyId(), request.getCeoName());
        return ResponseEntity.ok("CEO name updated successfully");
    }

    @PostMapping("/updateCompanyAddress")
    public ResponseEntity<String> updateCompanyAddress(@RequestBody UCmpInfDTO request) {
        companyService.updateCompanyAddress(request.getCompanyId(), request.getCompanyAddress());
        return ResponseEntity.ok("Company address updated successfully");
    }

    @PostMapping("/updateCompanyPostalCode")
    public ResponseEntity<String> updateCompanyPostalCode(@RequestBody UCmpInfDTO request) {
        companyService.updateCompanyPostalCode(request.getCompanyId(), request.getCompanyPostalCode());
        return ResponseEntity.ok("Company postal code updated successfully");
    }

    @PostMapping("/updateListingDate")
    public ResponseEntity<String> updateListingDate(@RequestBody UCmpInfDTO request) {
        companyService.updateListingDate(request.getCompanyId(), request.getListingDate());
        return ResponseEntity.ok("Listing date updated successfully");
    }

    @PostMapping("/updateListingStatus")
    public ResponseEntity<String> updateListingStatus(@RequestBody UCmpInfDTO request) {
        companyService.updateListingStatus(request.getCompanyId(), request.getListingStatus());
        return ResponseEntity.ok("Listing status updated successfully");
    }

    // 특정 companyId로 RCom 데이터 조회
    @GetMapping("showAllCombineByCmp/{companyId}")
    public ResponseEntity<List<RComDTO>> getRComByCompanyId(@PathVariable String companyId) {
        logger.info("RCom 데이터 조회 요청: companyId={}", companyId);

        List<RComDTO> rComDTOS = combineService.getRComByCompanyId(companyId);

        logger.info("RCom 데이터 조회 완료: {}", rComDTOS);
        return ResponseEntity.ok(rComDTOS);
    }
}
