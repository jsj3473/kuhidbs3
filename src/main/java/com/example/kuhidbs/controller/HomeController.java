package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.ContactDTO;
import com.example.kuhidbs.dto.company.ReadCompanyDTO;
import com.example.kuhidbs.dto.company.ReviewerDTO;
import com.example.kuhidbs.dto.user.UserDTO;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.service.UserService;
import com.example.kuhidbs.service.company.CompanyService;
import com.example.kuhidbs.service.company.ContactService;
import com.example.kuhidbs.service.company.ReviewerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private ContactService contactService;
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
    // 기업 정보관리 페이지
    @GetMapping("/companyManage")
    public String companyManagePage(Model model) {
        // CompanyService를 사용하여 데이터 조회
        List<ReadCompanyDTO> companyList = companyService.getAllCompanies();
        model.addAttribute("companies", companyList); // 데이터를 모델에 추가
        model.addAttribute("title", "기업 정보관리");


        return "companyManage"; // companyManage.html로 이동
    }
    // 기본정보 팝업(실무자, 심사역)
    @GetMapping("/company/basicInfo/{id}")
    public String showBasicInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("companyId", id);
        List<ReviewerDTO> reviewerDTOList = reviewerService.getAllReviewersByCompanyID(id);
        model.addAttribute("reviewers", reviewerDTOList);
        List<ContactDTO> contactDTOList = contactService.getAllContactsByCompanyID(id);
        model.addAttribute("contacts", contactDTOList);
        return "company/basicInfo"; // basicInfo.html
    }
    // 후속관리 팝업
    @GetMapping("/company/followup/{id}")
    public String showFollowupPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/followup"; // followup.html
    }
    // 재무 및 인력상황
    @GetMapping("/company/financial/{id}")
    public String financialInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/financial"; // financial.html
    }
    // 회수성과 팝업
    @GetMapping("/company/recovery/{id}")
    public String recoveryInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("companyId", id);
        return "company/recovery"; // recovery.html
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
}
