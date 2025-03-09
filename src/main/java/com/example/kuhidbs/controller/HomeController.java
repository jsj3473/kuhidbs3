package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.Fund.*;
import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.dto.company.감액환입.RShrupDTO;
import com.example.kuhidbs.dto.company.동반.RComDTO;
import com.example.kuhidbs.dto.company.무증.RBonusDTO;
import com.example.kuhidbs.dto.company.팁스.발심사.RRwrDTO;
import com.example.kuhidbs.dto.company.사후관리.RMngDTO;
import com.example.kuhidbs.dto.company.역사.RAccountDTO;
import com.example.kuhidbs.dto.company.재무.RFncDTO;
import com.example.kuhidbs.dto.company.주주명부.RShrDTO;
import com.example.kuhidbs.dto.company.팁스.RTIPSDTO;
import com.example.kuhidbs.dto.company.회수.RstcupDTO;
import com.example.kuhidbs.dto.company.후속투자.RFolDTO;
import com.example.kuhidbs.dto.user.UserDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.service.Fund.*;
import com.example.kuhidbs.service.UserService;
import com.example.kuhidbs.service.company.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private FollowupService followupService;
    @Autowired
    private ShrupService shrupService;
    @Autowired
    private BonusService bonusService;
    @Autowired
    private RecoveryService recoveryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CombineService combineService;
    @Autowired
    private ShareholderService shareholderService;
    @Autowired
    private TipsService tipsService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private FinancialService financialService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private FundFinancialService fundFinancialService;
    @Autowired
    private FundService fundService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private FundMemService fundMemService;
    @Autowired
    private IASService iasService;
    @Autowired
    private DueDiligenceService dueDiligenceService;
    @Autowired
    private EmploymentService employmentService;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private FundAchievementService fundAchievementService;



    //공통 데이터 설정
    @ModelAttribute
    public void addCommonAttributes(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("empId", loggedInUser.getId());
            model.addAttribute("empName", loggedInUser.getName());
        }
    }

    // 기본 URL ("/") 요청 시 로그인 페이지로 리다이렉트
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }

    //로그인
    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // login.html
    }

    // 사용자 등록 팝업
    @GetMapping("/system/createUser")
    public String createUserPage() {
        return "system/createUser"; // createUser.html
    }



    @GetMapping("/main")
    public String mainPage( Model model, HttpSession session) {
        model.addAttribute("message", "Welcome to KUH IDBS!");
        model.addAttribute("title", "메인 페이지");

        return "main"; // main.html
    }

    //사용자 관리페이지
    @GetMapping("system/userManage")
    public String userManagePage(Model model) {
        model.addAttribute("pageTitle", "사원 관리");
        model.addAttribute("title", "사용자관리");
        List<UserDTO> userDTOList = userService.getAllUsers();
        model.addAttribute("users", userDTOList);
        return "system/userManage"; // userManage.html
    }


// ====================================
//  기업관리
// ====================================


    // 신규기업등록 페이지
    @GetMapping("/companyAdd")
    public String companyAddPage(Model model) {
        model.addAttribute("title", "신규기업등록");
        return "companyAdd"; // companyAdd.html
    }

    // KUH 투자정보 팝업
    @GetMapping("/companyAdd/kuhInvestment/{id}")
    public String kuhInvestment(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RFundNameDTO> funds = fundService.getAllFundIdAndName();
        model.addAttribute("funds", funds);
        return "companyAdd/kuhInvestment"; // kuhInvestment.html
    }

    // 후속투자 생성
    @GetMapping("/companyAdd/followupInvestment/{id}")
    public String followupInvestment(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        RShrDTO shrDTO = Optional.ofNullable(shareholderService.getShareholderByCompanyId(id))
                .orElse(new RShrDTO()); // 기본 빈 DTO 객체 생성

        model.addAttribute("shrDTO", shrDTO);

        return "companyAdd/followupInvestment"; // followupInvestment.html
    }

    // 투자감액/환입 입력 팝업
    @GetMapping("/companyAdd/shareUp/{companyId}/{investmentId}")
    public String shareUp(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/shareUp"; // shareUp.html
    }

    // 무상증자 입력 팝업
    @GetMapping("/companyAdd/bonus/{companyId}/{investmentId}")
    public String bonus(@PathVariable("companyId") String companyId,
                        @PathVariable("investmentId") String investmentId,
                        Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        RShrDTO shrDTO = Optional.ofNullable(shareholderService.getShareholderByCompanyId(companyId))
                .orElse(new RShrDTO()); // 기본 빈 DTO 객체 생성
        model.addAttribute("shrDTO", shrDTO);
        return "companyAdd/bonus"; // bonus.html
    }

    // 동반투자 입력 팝업
    @GetMapping("/companyAdd/combine/{companyId}/{investmentId}")
    public String combine(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/combine"; // companion.html
    }

    // 투자회수 입력 팝업
    @GetMapping("/companyAdd/stockUp/{companyId}/{investmentId}")
    public String stockUp(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        RShrDTO shrDTO = Optional.ofNullable(shareholderService.getShareholderByCompanyId(companyId))
                .orElse(new RShrDTO()); // 기본 빈 DTO 객체 생성
        model.addAttribute("shrDTO", shrDTO);
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/stockUp"; // stockUp.html
    }
    // 투자감액/환입 입력 팝업
    @GetMapping("/companyAdd/dueDiligence/{companyId}/{investmentId}")
    public String dueDiligence(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/dueDiligence"; // dueDiligence.html
    }

    // 투자상태 입력 팝업
    @GetMapping("/companyAdd/investmentStatus/{id}")
    public String investmentStatus(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/investmentStatus"; // investmentStatus.html
    }
    // 팁스현황 입력 팝업
    @GetMapping("/companyAdd/tips/{id}")
    public String tips(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);

        // ✅ 서비스에서 해당 기업의 팁스 데이터를 가져옴
        RTIPSDTO rTipsDTO = tipsService.getTipsByCompanyId(id);

        // ✅ rTipsDTO가 null이면 "companyUpdate/tips"로 반환
        if (rTipsDTO == null) {
            return "companyAdd/tips"; // tips.html
        }

        model.addAttribute("rTipsDTO", rTipsDTO);
        return "companyUpdate/tips";
    }

    // 재무제표 입력 팝업
    @GetMapping("/companyAdd/financial/{id}")
    public String financial(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/financial"; // financial.html
    }
    // 사후관리 입력 팝업
    @GetMapping("/companyAdd/manage/{id}")
    public String manage(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/manage"; // manage.html
    }

    // 액면분할 입력 팝업
    @GetMapping("/companyAdd/stockSplit/{id}")
    public String stockSplit(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/stockSplit"; // stockSplit.html
    }

    // 사후관리담당자 입력 팝업
    @GetMapping("/companyAdd/managerChange/{id}")
    public String managerChange(@PathVariable("id") String id, Model model) {
        //System.out.println("Received companyId: " + id);  // 로그 출력
        model.addAttribute("companyId", id);
        return "companyAdd/managerChange"; // managerChange.html
    }


    // 기업 전체조회 페이지
    @GetMapping("/companyShow/{id}")
    public String companyShow(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyShow"; // companyShow.html
    }

    // 기업별 모든 투자 전체조회 페이지
    @GetMapping("/companyShow/ivtByCmp/{id}")
    public String companyShowIvtByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RIvtDTO> investments = Optional.ofNullable(investmentService.getAllInvestmentsByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("investments", investments);
        return "companyShow/ivtByCmp";
    }

    // 기업별 모든 후속투자 전체조회 페이지
    @GetMapping("/companyShow/folByCmp/{id}")
    public String companyShowFolByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RFolDTO> followups = Optional.ofNullable(followupService.getFollowupByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("followups", followups);
        return "companyShow/folByCmp";
    }

    // 기업별 모든 감액환입 전체조회 페이지
    @GetMapping("/companyShow/shrupByCmp/{id}")
    public String companyShowShrupByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RShrupDTO> shrupDTOS = Optional.ofNullable(shrupService.getAllShrupsByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("shrupDTOS", shrupDTOS);
        return "companyShow/shrupByCmp";
    }

    // 기업별 모든 회수 전체조회 페이지
    @GetMapping("/companyShow/recoveryByCmp/{id}")
    public String companyShowRecoveryByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RstcupDTO> rstcupDTOS = Optional.ofNullable(recoveryService.getAllstcupByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rstcupDTOS", rstcupDTOS);
        return "companyShow/recoveryByCmp";
    }

    // 기업별 모든 무상증자 전체조회 페이지
    @GetMapping("/companyShow/bonusByCmp/{id}")
    public String companyShowBonusByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RBonusDTO> rBonusDTOS = Optional.ofNullable(bonusService.getAllBonusByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rBonusDTOS", rBonusDTOS);
        return "companyShow/bonusByCmp";
    }

    // 기업별 모든 팁스 전체조회 페이지
    @GetMapping("/companyShow/tipsByCmp/{id}")
    public String companyShowTipsByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        RTIPSDTO rTipsDTO = Optional.ofNullable(tipsService.getTipsByCompanyId(id))
                .orElseGet(RTIPSDTO::new);
        model.addAttribute("rTipsDTO", rTipsDTO);
        return "companyShow/tipsByCmp";
    }

    // 기업별 모든 재무제표 전체조회 페이지
    @GetMapping("/companyShow/financialByCmp/{id}")
    public String companyShowFinancialByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RFncDTO> rFncDTOS = Optional.ofNullable(financialService.getAllFinancialsByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rFncDTOS", rFncDTOS);
        return "companyShow/financialByCmp";
    }

    // 기업별 모든 사후관리 전체조회 페이지
    @GetMapping("/companyShow/manageByCmp/{id}")
    public String companyShowManageByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        // 모든 데이터 조회
        List<RMngDTO> rMngDTOS =  Optional.ofNullable(manageService.getAllManageByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rMngDTOS", rMngDTOS);
        return "companyShow/manageByCmp";
    }

    // 기업별 모든 사후담당자 전체조회 페이지
    @GetMapping("/companyShow/reviewerChangeByCmp/{id}")
    public String companyShowManagerChangeByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RRwrDTO> rRwrDTOS = Optional.ofNullable(reviewerService.getReviewersByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rRwrDTOS", rRwrDTOS);
        return "companyShow/reviewerChangeByCmp";
    }

    // 기업별 모든 공동투자내역 전체조회 페이지
    @GetMapping("/companyShow/combineByCmp/{id}")
    public String companyShowCombineByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RComDTO> rComDTOS = Optional.ofNullable(combineService.getRComByCompanyId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rComDTOS", rComDTOS);
        return "companyShow/combineByCmp";
    }

    // 투자별 계좌조회 (투자 ID 값 사용)
    @GetMapping("/companyShow/accountByIvt/{id}")
    public String companyShowAccountByIvt(@PathVariable("id") Long id, Model model) {
        model.addAttribute("investmentId", id);
        List<RAccountDTO> rAccountDTOS = Optional.ofNullable(accountService.getAllAccountsByInvestmentId(id))
                .orElseGet(ArrayList::new);
        model.addAttribute("rAccountDTOS", rAccountDTOS);
        return "companyShow/accountByIvt";
    }


    // 기업후속관리 페이지
    @GetMapping("/companyManage/{id}")
    public String companyManage(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyManage"; // companyManage.html
    }


// ====================================
//  투자조합(펀드)
// ====================================

    // 조합 신규등록 페이지
    @GetMapping("/fundAdd")
    public String fundAddPage(Model model) {
        model.addAttribute("title", "신규조합등록");
        return "fundAdd"; // fundAdd.html
    }
    // 회계감사인 입력 팝업
    @GetMapping("/fundAdd/audit/{id}")
    public String audit(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        return "fundAdd/audit"; // audit.html
    }
    // 운용인력 입력 팝업
    @GetMapping("/fundAdd/staff/{id}")
    public String staff(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        return "fundAdd/staff"; // staff.html
    }
    // 조합원명부 입력 팝업
    @GetMapping("/fundAdd/member/{id}")
    public String member(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RFundMemDTO> rFundMemDTOS = fundMemService.getActiveFundMembersByFundId(id);
        model.addAttribute("rFundMemDTOS", rFundMemDTOS);
        // 펀드 정보 조회
        Fund fund = fundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("펀드를 찾을 수 없습니다. ID: " + id));
        model.addAttribute("fund", fund);
        return "fundAdd/member"; // member.html
    }
    // 조합재무제표 입력 팝업
    @GetMapping("/fundAdd/fundFinancial/{id}")
    public String fundFinancial(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        return "fundAdd/fundFinancial"; // fundFinancial.html
    }



    // 회계감사인 조회 팝업
    @GetMapping("/fundShow/auditsByFund/{id}")
    public String auditsByFund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RAuditDTO> auditDTOS = auditService.getAuditsByFundId(id);
        model.addAttribute("auditDTOS", auditDTOS);
        return "fundShow/auditsByFund"; // auditsByFund.html
    }
    // 운용인력 조회 팝업
    @GetMapping("/fundShow/staffsByFund/{id}")
    public String staffsByFund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RStaffDTO> staffDTOS = staffService.getStaffChangesByFundId(id);
        model.addAttribute("staffDTOS", staffDTOS);
        return "fundShow/staffsByFund"; // staffsByFund.html
    }
    // 조합재무제표 조회 팝업
    @GetMapping("/fundShow/financialsByFund/{id}")
    public String financialsByFund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RFundFinancialDTO> rFundFinancialDTOS = fundFinancialService.getFundFinancialsByFundId(id);
        model.addAttribute("rFundFinancialDTOS", rFundFinancialDTOS);
        return "fundShow/financialsByFund"; // financialsByFund.html
    }

    // 투자비율 달성여부 조회 팝업
    @GetMapping("/fundShow/achievementByfund/{id}")
    public String achievementByfund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        RFundAchievementDTO rFundAchievementDTOS = fundAchievementService.getFundAchievement(id);
        model.addAttribute("rIASDTOS", rFundAchievementDTOS);
        return "fundShow/achievementByfund"; // achievementByfund.html
    }
    // 투자자산총괄표 조회 팝업
    @GetMapping("/fundShow/IASByfund/{id}")
    public String IASByfund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RIASDTO> rIASDTOS = iasService.getInvestmentAssetSummaryByFundId(id);
        model.addAttribute("rIASDTOS", rIASDTOS);
        return "fundShow/IASByfund"; // IASByfund.html
    }

    // 투자기업실사 조회 팝업
    @GetMapping("/fundShow/dueDiligenceByFund/{id}")
    public String dueDiligenceByFund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<RDueDiligenceDTO> rDueDiligenceDTOS = dueDiligenceService.getDueDiligenceByFundId(id);
        model.addAttribute("rDueDiligenceDTOS", rDueDiligenceDTOS);
        return "fundShow/dueDiligenceByFund"; // dueDiligenceByFund.html
    }

    // 투자기업 고용변경 조회 팝업
    @GetMapping("/fundShow/empChangeByFund/{id}")
    public String empChangeByFund(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        List<REmploymentDTO> rEmploymentDTOS = employmentService.showAllEmployment(id);
        model.addAttribute("rEmploymentDTOS", rEmploymentDTOS);
        return "fundShow/empChangeByFund"; // empChangeByFund.html
    }

    // 펀드 전체조회 페이지
    @GetMapping("/fundShow/{id}")
    public String fundShow(@PathVariable("id") String id, Model model) {
        model.addAttribute("fundId", id);
        return "fundShow"; // fundShow.html
    }

//    // 펀드 조합후속관리 페이지
//    @GetMapping("/fundManage/{id}")
//    public String fundManage(@PathVariable("id") String id, Model model) {
//        model.addAttribute("fundId", id);
//        return "fundManage"; // fundManage.html
//    }

    // 조합원 명부 구성 조회 팝업
    @GetMapping("/fundShow/membersByFund/{id}")
    public String membersByFund(@PathVariable("id") String id, Model model) {
        List<RMemtypeDTO> rMemtypeDTOS = fundMemService.getActiveFundMembers2ByFundId(id);
        model.addAttribute("rMemtypeDTOS", rMemtypeDTOS);
// 펀드 정보 조회
        Fund fund = fundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("펀드를 찾을 수 없습니다. ID: " + id));
        model.addAttribute("fund", fund);

        return "fundShow/membersByFund"; // membersByFund.html
    }
}