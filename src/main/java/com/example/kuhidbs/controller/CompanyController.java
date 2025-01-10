package com.example.kuhidbs.controller;


import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany(@ModelAttribute CreateCompanyDTO createCompanyDTO) {

        // Service 호출
        Company createdCompany = companyService.createCompany(createCompanyDTO);

        // HTTP 상태 코드와 함께 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }    // Show all companies





}
