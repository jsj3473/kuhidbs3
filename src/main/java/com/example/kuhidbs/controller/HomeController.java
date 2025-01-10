package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.ContactDTO;
import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.dto.company.ReadCompanyDTO;
import com.example.kuhidbs.dto.company.ReviewerDTO;
import com.example.kuhidbs.service.company.CompanyService;
import com.example.kuhidbs.service.company.ContactService;
import com.example.kuhidbs.service.company.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private ContactService contactService;

    // 공통 데이터 설정
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("empId", "000000");
        model.addAttribute("empName", "김영진");
    }

    @GetMapping("/")
    public String login(Model model) {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String loginSubmit() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("message", "Welcome to KUH IDBS!");
        model.addAttribute("title", "메인 페이지");
        return "main"; // main.html
    }

    @GetMapping("/userManage")
    public String userManagePage(Model model) {
        model.addAttribute("pageTitle", "사원 관리");
        model.addAttribute("title", "사용자관리");
        return "userManage"; // userManage.html
    }
    @GetMapping("/companyAdd")
    public String companyAddPage(Model model) {
        model.addAttribute("title", "기업 신규등록");
        return "companyAdd"; // companyAdd.html
    }

    @GetMapping("/companyManage")
    public String companyManagePage(Model model) {
        // CompanyService를 사용하여 데이터 조회
        List<ReadCompanyDTO> companyList = companyService.getAllCompanies();
        model.addAttribute("companies", companyList); // 데이터를 모델에 추가
        model.addAttribute("title", "기업 정보관리");


        return "companyManage"; // companyManage.html로 이동
    }
    @GetMapping("/company/basicInfo/{id}")
    public String showBasicInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("companyId", id);
        List<ReviewerDTO> reviewerDTOList = reviewerService.getAllReviewersByCompanyID(id);
        model.addAttribute("reviewers", reviewerDTOList);
        List<ContactDTO> contactDTOList = contactService.getAllContactsByCompanyID(id);
        model.addAttribute("contacts", contactDTOList);
        return "company/basicInfo"; // basicInfo.html
    }

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
}
