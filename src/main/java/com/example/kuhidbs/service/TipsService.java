package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CTIPSDTO;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.entity.TIPS;
import com.example.kuhidbs.repository.CompanyRepository;
import com.example.kuhidbs.repository.TipsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class TipsService {

    private final TipsRepository tipsRepository;
    private final CompanyRepository companyRepository;

    // TIPS 생성
    @Transactional
    public void createTips(CTIPSDTO dto) {
        // 회사 조회 (외래키 검증)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // DTO → Entity 변환 후 저장
        TIPS tips = TIPS.builder()
                .company(company)
                .tipsComment(dto.getTipsComment())
                .tipsSelectionDate(dto.getTipsSelectionDate())
                .tipsSelectionType(dto.getTipsSelectionType())
                .executionStartDate(dto.getExecutionStartDate())
                .executionEndDate(dto.getExecutionEndDate())
                .selectionYesNo(dto.getSelectionYesNo())
                .tipsManagementEndDate(dto.getTipsManagementEndDate())
                .successYesNo(dto.getSuccessYesNo())
                .followTips(dto.getFollowTips())
                .kips(dto.getKips())
                .build();

        tipsRepository.save(tips);
    }

    // 날짜 변환 메서드 (문자열 → LocalDate)
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
}
