package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.dto.company.감액환입.RShrupDTO;
import com.example.kuhidbs.dto.company.무증.RBonusDTO;
import com.example.kuhidbs.dto.company.역사.RAccountDTO;
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
        return "companyAdd/followupInvestment"; // followupInvestment.html
    }

    // 투자감액/복원입력 팝업
    @GetMapping("/companyAdd/shareUp/{companyId}/{investmentId}")
    public String shareUp(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/shareUp"; // shareUp.html
    }

    // 무상증자 팝업
    @GetMapping("/companyAdd/bonus/{companyId}/{investmentId}")
    public String bonus(@PathVariable("companyId") String companyId,
                        @PathVariable("investmentId") String investmentId,
                        Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/bonus"; // bonus.html
    }

    // 동반투자 팝업
    @GetMapping("/companyAdd/combine/{companyId}/{investmentId}")
    public String combine(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/combine"; // companion.html
    }

    // 기업 전체조회 페이지
    @GetMapping("/info/{id}")
    public String info(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "info"; // info.html
    }

    // 기업별 모든 투자 전체조회 페이지
    @GetMapping("/showIvtByCmp/{id}")
    public String showIvtByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RIvtDTO> investments = investmentService.getAllInvestmentsByCompanyId(id);
        model.addAttribute("investments", investments);
        return "showIvtByCmp"; // info.html
    }

    // 기업별 모든 후속투자 전체조회 페이지
    @GetMapping("/showFolByCmp/{id}")
    public String showFolByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RFolDTO> followups = followupService.getFollowupByCompanyId(id);

        model.addAttribute("followups", followups);
        return "showFolByCmp"; // info.html
    }
    // 기업별 모든 감액환입 전체조회 페이지
    @GetMapping("/showShrupByCmp/{id}")
    public String showShrupByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RShrupDTO> shrupDTOS = shrupService.getAllShrupsByCompanyId(id);

        model.addAttribute("shrupDTOS", shrupDTOS);
        return "showShrupByCmp"; // info.html
    }
    // 기업별 모든 회수 전체조회 페이지
    @GetMapping("/showRecoveryByCmp/{id}")
    public String showRecoveryByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RstcupDTO> rstcupDTOS = recoveryService.getAllstcupByCompanyId(id);

        model.addAttribute("rstcupDTOS", rstcupDTOS);
        return "showRecoveryByCmp"; // info.html
    }
    // 기업별 모든 무상증자  전체조회 페이지
    @GetMapping("/showBonusByCmp/{id}")
    public String showBonusByCmp(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        List<RBonusDTO> rBonusDTOS = bonusService.getAllBonusByCompanyId(id);

        model.addAttribute("rBonusDTOS", rBonusDTOS);
        return "showBonusByCmp"; // info.html
    }
    //투자별 계좌조회
    @GetMapping("/showAccountByIvt/{id}")
    public String showBonusByCmp(@PathVariable("id") Long id, Model model) {
        model.addAttribute("investmentId", id);
        List<RAccountDTO> rAccountDTOS = accountService.getAllAccountsByInvestmentId(id);

        model.addAttribute("rAccountDTOS", rAccountDTOS);
        return "showAccountByIvt"; // info.html
    }



//지
//    @GetMapping("/companyRead/{id}")
//    public String companyRead(@PathVariable("id") String id, Model model) {
//        System.out.println("📢 companyRead() 호출 - companyId: " + id);
//        model.addAttribute("companyId", id);
//        return "companyRead"; // info.html
//    }
//


    // 재무 및 인력상황
    @GetMapping("/company/financial/{id}")
    public String financialInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/financial"; // financial.html
    }
    // 회수성과 팝업
    @GetMapping("/company/recovery/{companyId}/{investmentId}")
    public String recoveryInfo(@PathVariable("companyId") String companyId,
                               @PathVariable("investmentId") String investmentId, Model model) {

        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/recovery"; // recovery.html
    }

    // TIPS 팝업
    @GetMapping("/company/tips/{id}")
    public String tipsInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/tips"; // tips.html
    }

    // 주주명부 팝업
    @GetMapping("/company/shareholder/{id}")
    public String shareholderInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/shareholder"; // shareholder.html
    }

    // 사업진행현황 팝업
    @GetMapping("/compaInvestmentServiceny/progress/{id}")
    public String progressInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/progress"; // progress.html
    }

    // 투자회수 팝업
    @GetMapping("/companyAdd/stockUp/{companyId}/{investmentId}")
    public String stockUp(@PathVariable("companyId") String companyId,
                          @PathVariable("investmentId") String investmentId, Model model) {

        model.addAttribute("companyId", companyId);
        model.addAttribute("investmentId", investmentId);
        return "companyAdd/stockUp"; // stockUp.html
    }
    // 투자상태 팝업
    @GetMapping("/companyAdd/investmentStatus/{id}")
    public String investmentStatus(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/investmentStatus"; // investmentStatus.html
    }
    // 팁스현황 팝업
    @GetMapping("/companyAdd/tips/{id}")
    public String tips(@PathVariable("id") String id, Model model) {
        model.addAttribute("companyId", id);
        return "companyAdd/tips"; // tips.html
    }
}