package com.example.kuhidbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("message", "Welcome to KUH IDBS!");
        return "main"; // main.html
    }

    @GetMapping("/")
    public String login(Model model) {
        return "login"; // login.html
    }

    @GetMapping("/userManage")
    public String userManagePage(Model model) {
        // 필요한 데이터가 있다면 model에 추가
        model.addAttribute("pageTitle", "사원 관리");
        return "userManage"; // userManage.html
    }
}
