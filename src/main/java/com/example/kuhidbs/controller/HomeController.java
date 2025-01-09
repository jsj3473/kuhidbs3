package com.example.kuhidbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

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
}
