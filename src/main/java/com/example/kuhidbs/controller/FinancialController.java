package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.financial.CreateFinancialDTO;
import com.example.kuhidbs.entity.Financial;
import com.example.kuhidbs.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    /**
     * 재무 데이터 생성
     * @param createFinancialDTO 재무 데이터 DTO
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> createFinancial(@RequestBody CreateFinancialDTO createFinancialDTO) {
        financialService.createFinancial(createFinancialDTO);
        return ResponseEntity.ok("Financial data created successfully.");
    }

    // Get all financial records
    @GetMapping
    public ResponseEntity<List<Financial>> getAllFinancials() {
        List<Financial> financials = financialService.getAllFinancials();
        return ResponseEntity.ok(financials);
    }
}
