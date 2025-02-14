package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.user.UserDTO;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.service.UserService;
import com.example.kuhidbs.service.company.CompanyService;
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

    // 후속투자 팝업
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
    @GetMapping("/company/progress/{id}")
    public String progressInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/progress"; // progress.html
    }

    // 투자회수 팝업
    @GetMapping("/companyAdd/stockUp/{id}")
    public String stockUp(@PathVariable("id") String id, Model model) {
        model.addAttribute("stockUp", id);
        return "companyAdd/stockUp"; // stockUp.html
    }
    // 투자상태 팝업
    @GetMapping("/companyAdd/investmentStatus/{id}")
    public String investmentStatus(@PathVariable("id") String id, Model model) {
        model.addAttribute("investmentStatus", id);
        return "companyAdd/investmentStatus"; // investmentStatus.html
    }
    // 팁스현황 팝업
    @GetMapping("/companyAdd/tips/{id}")
    public String tips(@PathVariable("id") String id, Model model) {
        model.addAttribute("tips", id);
        return "companyAdd/tips"; // tips.html
    }
}