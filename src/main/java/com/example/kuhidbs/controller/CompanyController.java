package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.CreateCompanyDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.service.CompanyService;
import com.example.kuhidbs.utility.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public Company createCompany(@RequestBody CreateCompanyDTO createCompanyDTO) {
        Company company = CompanyMapper.toEntity(createCompanyDTO);
        return companyService.createCompany(company);
    }


}
