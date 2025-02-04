package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.*;
import com.example.kuhidbs.entity.*;
import com.example.kuhidbs.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageService {

    @Autowired
    private ManageRepository manageRepository;

    @Autowired
    private CmpRepository companyRepository;

    // 사후관리 정보 생성
    @Transactional
    public Manage createManage(CMngDTO dto) {
        // 회사 조회 (외래키 확인)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // DTO → Entity 변환 후 저장
        Manage manage = Manage.builder()
                .company(company)
                .evalGrade(dto.getEvalGrade())
                .businessProgress1(dto.getBusinessProgress1())
                .businessProgress2(dto.getBusinessProgress2())
                .businessProgress3(dto.getBusinessProgress3())
                .businessProgress4(dto.getBusinessProgress4())
                .businessProgress5(dto.getBusinessProgress5())
                .managementStatus1(dto.getManagementStatus1())
                .managementStatus2(dto.getManagementStatus2())
                .exitPlan(dto.getExitPlan())
                .exitEstimation(dto.getExitEstimation())
                .build();

        return manageRepository.save(manage);
    }
}