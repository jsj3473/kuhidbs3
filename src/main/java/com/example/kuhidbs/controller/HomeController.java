package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.dto.company.감액환입.RShrupDTO;
import com.example.kuhidbs.dto.company.무증.RBonusDTO;
import com.example.kuhidbs.dto.company.발심사.RRwrDTO;
import com.example.kuhidbs.dto.company.사후관리.RMngDTO;
import com.example.kuhidbs.dto.company.역사.RAccountDTO;
import com.example.kuhidbs.dto.company.재무.RFncDTO;
import com.example.kuhidbs.dto.company.주주명부.RShrDTO;
import com.example.kuhidbs.dto.company.팁스.RTIPSDTO;
import com.example.kuhidbs.dto.company.회수.RstcupDTO;
import com.example.kuhidbs.dto.company.후속투자.RFolDTO;
import com.example.kuhidbs.dto.user.UserDTO;
import com.example.kuhidbs.entity.User;
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
    private ShareholderService shareholderService;
    @Autowired
    private TipsService tipsService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private FinancialService financialService;
    @Autowired
    private ReviewerService reviewerService;

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
    // 기업 신규등록 페이지
    @GetMapping("/companyAdd")
    public String companyAddPage(Model model) {
        model.addAttribute("title", "기업 신규등록");
        return "companyAdd"; // companyAdd.html
    }

    // KUH 투자정보 팝업
    @GetMapping("/companyAdd/kuhInvestment/{id}")
    public String kuhInvestment(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/kuhInvestment"; // kuhInvestment.html
    }

    // 후속투자 생성
    @GetMapping("/companyAdd/followupInvestment/{id}")
    public String followupInvestment(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        RShrDTO shrDTO = shareholderService.getShareholderByCompanyId(id);
        model.addAttribute("shrDTO", shrDTO);
        return "companyAdd/followupInvestment"; // followupInvestment.html
    }

    // 투자감액/복원 입력 팝업
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
        RShrDTO shrDTO = shareholderService.getShareholderByCompanyId(companyId);
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
        RShrDTO shrDTO = shareholderService.getShareholderByCompanyId(companyId);
        model.addAttribute("shrDTO", shrDTO);
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/stockUp"; // stockUp.html
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

    // 사후관리담당자 입력 팝업
    @GetMapping("/companyAdd/managerChange/{id}")
    public String managerChange(@PathVariable("id") String id, Model model) {
        System.out.println("Received companyId: " + id);  // 로그 출력
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
        List<RIvtDTO> investments = investmentService.getAllInvestmentsByCompanyId(id);
        model.addAttribute("investments", investments);
        return "companyShow/ivtByCmp"; // ivtByCmp.html
    }

    // 기업별 모든 후속투자 전체조회 페이지
    @GetMapping("/companyShow/folByCmp/{id}")
    public String companyShowFolByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RFolDTO> followups = followupService.getFollowupByCompanyId(id);

        model.addAttribute("followups", followups);
        return "companyShow/folByCmp"; // folByCmp.html
    }
    // 기업별 모든 감액환입 전체조회 페이지
    @GetMapping("/companyShow/shrupByCmp/{id}")
    public String companyShowShrupByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RShrupDTO> shrupDTOS = shrupService.getAllShrupsByCompanyId(id);

        model.addAttribute("shrupDTOS", shrupDTOS);
        return "companyShow/shrupByCmp"; // shrupByCmp.html
    }
    // 기업별 모든 회수 전체조회 페이지
    @GetMapping("/companyShow/recoveryByCmp/{id}")
    public String companyShowRecoveryByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RstcupDTO> rstcupDTOS = recoveryService.getAllstcupByCompanyId(id);

        model.addAttribute("rstcupDTOS", rstcupDTOS);
        return "companyShow/recoveryByCmp"; // recoveryByCmp.html
    }
    // 기업별 모든 무상증자  전체조회 페이지
    @GetMapping("/companyShow/bonusByCmp/{id}")
    public String companyShowBonusByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RBonusDTO> rBonusDTOS = bonusService.getAllBonusByCompanyId(id);

        model.addAttribute("rBonusDTOS", rBonusDTOS);
        return "companyShow/bonusByCmp"; // bonusByCmp.html
    }
    // 기업별 모든 팁스 전체조회 페이지
    @GetMapping("/companyShow/tipsByCmp/{id}")
    public String companyShowTipsByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        RTIPSDTO rTipsDTO = tipsService.getTipsByCompanyId(id);
        model.addAttribute("rTipsDTO", rTipsDTO);
        return "companyShow/tipsByCmp"; // tipsByCmp.html
    }
    // 기업별 모든 재무제표 전체조회 페이지
    @GetMapping("/companyShow/financialByCmp/{id}")
    public String companyShowFinancialByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);

        // ✅ 재무제표 데이터를 서비스에서 가져오기
        List<RFncDTO> rFncDTOS = financialService.getAllFinancialsByCompanyId(id);
        model.addAttribute("rFncDTOS", rFncDTOS);

        return "companyShow/financialByCmp"; // financialByCmp.html
    }
    // 기업별 모든 사후관리 전체조회 페이지
    @GetMapping("/companyShow/manageByCmp/{id}")
    public String companyShowManageByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        RMngDTO rMngDTOS = manageService.getManageByCompanyId(id);
        model.addAttribute("rMngDTOS", rMngDTOS);
        return "companyShow/manageByCmp"; // manageByCmp.html
    }

    // 기업별 모든 사후담당자 전체조회 페이지
    @GetMapping("/companyShow/reviewerChangeByCmp/{id}")
    public String companyShowManagerChangeByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RRwrDTO> rRwrDTOS = reviewerService.getReviewersByCompanyId(id);
        model.addAttribute("rRwrDTOS", rRwrDTOS);
        return "companyShow/reviewerChangeByCmp"; // reviewerChangeByCmp.html
    }


    //투자별 계좌조회 //투자 id 값 사용
    @GetMapping("/companyShow/accountByIvt/{id}")
    public String companyShowAccountByIvt(@PathVariable("id") Long id, Model model) {
        model.addAttribute("investmentId", id);
        List<RAccountDTO> rAccountDTOS = accountService.getAllAccountsByInvestmentId(id);

        model.addAttribute("rAccountDTOS", rAccountDTOS);
        return "companyShow/accountByIvt"; // accountByIvt.html
    }

    // 기업후속관리 페이지
    @GetMapping("/companyManage/{id}")
    public String companyManage(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyManage"; // companyManage.html
    }


}