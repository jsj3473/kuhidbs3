package com.example.kuhidbs.controller;


import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.service.company.CompanyService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> createCompany(@ModelAttribute CreateCompanyDTO createCompanyDTO, HttpServletResponse response) {
        try {
            // Service 호출
            companyService.createCompany(createCompanyDTO);

            // /companyManage로 리다이렉트
            response.sendRedirect("/companyManage");
            return ResponseEntity.status(HttpStatus.FOUND).build(); // 리다이렉트 상태 코드 반환
        } catch (IOException e) {
            throw new RuntimeException("Redirect failed", e);
        }
    }
   // Show all companies





}
