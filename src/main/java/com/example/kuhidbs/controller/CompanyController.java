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
import com.example.kuhidbs.service.Fund.FundService;
import com.example.kuhidbs.service.company.*;
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

    @PostMapping("/createCompany")
    public ResponseEntity<Void> createCompany(@ModelAttribute CCmpInfDTO CCmpInfDTO) {

        // 1. 회사 정보 저장 (Company 테이블)
        companyService.saveCompany(CCmpInfDTO);

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
    public ResponseEntity<Void> createInvestment(@RequestBody CIvtDTO cIvtDTO) {
        try {
            investmentService.saveInvestment(cIvtDTO);
            statusService.createStatusFirst(
                    cIvtDTO.getCompanyId(),
                    cIvtDTO.getInvestmentState(),
                    cIvtDTO.getInvestmentMemo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 성공 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }


    /**
     * 후속 투자 정보를 생성하는 API 엔드포인트.
     *
     * @param followupDto 후속 투자 정보가 담긴 DTO 객체
     * @return 저장된 Followup 객체
     */
    @PostMapping("/createFollowup")
    public ResponseEntity<Void> createFollowup(@ModelAttribute CFolDTO followupDto) {
        try {
            followupService.saveFollowup(followupDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }


    /**
     * 감액/복원 데이터 생성
     */
    @PostMapping("/createShrup")
    public ResponseEntity<Void> createShrup(@RequestBody CShrupDTO shrupDTO) {
        try {
            shrupService.saveShrup(shrupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }
    //회수 생성
    @PostMapping("/createStcup")
    public ResponseEntity<Void> createRecovery(@RequestBody CStcupDTO stcupDTO) {
        try {
            recoveryService.saveRecovery(stcupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }


    // 동반투자 생성
    @PostMapping("/createCombine")
    public ResponseEntity<Void> createCombine(@RequestBody CComDTO combineDTO) {
        try {
            combineService.saveCombine(combineDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    //주주생성
    @PostMapping("/createShareholder")
    public ResponseEntity<Void> createShareholder(@RequestBody CShrDTO shareholderDTO) {
        try {
            shareholderService.createShareholder(shareholderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    //재무제표생성
    @PostMapping("/createFinancial")
    public ResponseEntity<Void> createFinancialStatement(@RequestBody CFncDTO dto) {
        try {financialService.saveFinancialForCFncDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    // 사후관리 정보 생성 API
    @PostMapping("/createManage")
    public ResponseEntity<Void> createManage(@RequestBody CMngDTO dto) {
        try {
            manageService.createManage(dto);
            statusService.createStatusByManage(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    // Reviewer 데이터 생성 API
    @PostMapping("/createReviewer")
    public ResponseEntity<Void> createReviewer(@RequestBody CRwrDTO dto) {
        try {
            reviewerService.createReviewerForCRwrDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    // 무상증자 생성 API
    @PostMapping("/createBonus")
    public ResponseEntity<Void> createBonus(@RequestBody CBonusDTO dto) {
        try {
            bonusService.createBonus(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    // tips 데이터 생성 API
    @PostMapping("/createTIPS")
    public ResponseEntity<Void> createTIPS(@RequestBody CTIPSDTO dto) {
        try {
            tipsService.createTIPS(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
    }

    // 투자상태 데이터 생성 API
    @PostMapping("/createStatus")
    public ResponseEntity<Void> createStatus(@RequestBody CStatusDTO dto) {
        try {
            statusService.createStatus(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러 응답
        }
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



    // 팁스 정보 수정 API
    @PutMapping("/updateTIPS")
    public ResponseEntity<String> updateTips(@RequestBody UTIPSDTO dto) {
        tipsService.updateTips(dto);
        return ResponseEntity.ok("TIPS 정보가 업데이트되었습니다.");
    }

    // ✅ 기업 정보 수정 API (PUT 요청)
    @PutMapping("/updateCompany")
    public ResponseEntity<Company> updateCompany(@RequestBody UCmpInfDTO companyDTO) {
        Company updatedCompany = companyService.updateCompany(companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @GetMapping("/searchQueryForCompanyInHeader")
    public ResponseEntity<List<Object[]>> searchQueryForCompanyInHeader(@RequestParam String query) {
        List<Object[]> companyIds = companyRepository.searchCompanies(query);
        return ResponseEntity.ok(companyIds);
    }

}
