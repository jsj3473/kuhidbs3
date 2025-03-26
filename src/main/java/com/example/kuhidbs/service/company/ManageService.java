package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.mng.CMngDTO;
import com.example.kuhidbs.dto.company.mng.RMngDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Manage;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.ManageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManageService {

    @Autowired
    private ManageRepository manageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // 사후관리 정보 생성
    @Transactional
    public Manage createManage(CMngDTO dto) {
        // 회사 조회 (외래키 확인)
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // DTO → Entity 변환 후 저장
        Manage manage = Manage.builder()
                .company(company)
                .manageYear(dto.getManageYear())
                .halfYear(dto.getHalfYear())
                .evalGrade(dto.getEvalGrade())
                .businessProgress1(dto.getBusinessProgress1())
                .businessProgress2(dto.getBusinessProgress2())
                .businessProgress3(dto.getBusinessProgress3())
                .businessProgress4(dto.getBusinessProgress4())
                .businessProgress5(dto.getBusinessProgress5())
                .managementStatus1(dto.getManagementStatus1())
                .managementStatus2(dto.getManagementStatus2())
                .managementStatus3(dto.getManagementStatus3())
                .exitPlan1(dto.getExitPlan1())
                .exitPlan2(dto.getExitPlan2())
                .exitEstimation(dto.getExitEstimation())
                .build();

        return manageRepository.save(manage);
    }

    @Transactional(readOnly = true)
    public RMngDTO getTopManageByCompanyId(String companyId) {
        Optional<Manage> manageOpt = manageRepository.findTopByCompany_CompanyIdOrderByManageYearDesc(companyId);

        return manageOpt.map(manage -> RMngDTO.builder()
                .evalGrade(manage.getEvalGrade())
                .manageYear(manage.getManageYear())
                .halfYear(manage.getHalfYear())
                .evalGrade(manage.getEvalGrade())
                .businessProgress1(manage.getBusinessProgress1())
                .businessProgress2(manage.getBusinessProgress2())
                .businessProgress3(manage.getBusinessProgress3())
                .businessProgress4(manage.getBusinessProgress4())
                .businessProgress5(manage.getBusinessProgress5())
                .managementStatus1(manage.getManagementStatus1())
                .managementStatus2(manage.getManagementStatus2())
                .managementStatus3(manage.getManagementStatus3())
                .exitPlan1(manage.getExitPlan1())
                .exitPlan2(manage.getExitPlan2())
                .exitEstimation(manage.getExitEstimation())
                .createdAt(manage.getCreatedAt())
                .updatedAt(manage.getUpdatedAt())
                .createdBy(manage.getCreatedBy())
                .updatedBy(manage.getUpdatedBy())
                .build()).orElse(null);
    }


    // 기업별 전체 사후관리 데이터 조회
    public List<RMngDTO> getAllManageByCompanyId(String companyId) {
        return manageRepository.findAllByCompany_CompanyId(companyId).stream()
                .map(manage -> RMngDTO.builder()
                        .companyId(companyId)
                        .evalGrade(manage.getEvalGrade())
                        .manageYear(manage.getManageYear())
                        .halfYear(manage.getHalfYear())
                        .businessProgress1(manage.getBusinessProgress1())
                        .businessProgress2(manage.getBusinessProgress2())
                        .businessProgress3(manage.getBusinessProgress3())
                        .businessProgress4(manage.getBusinessProgress4())
                        .businessProgress5(manage.getBusinessProgress5())
                        .managementStatus1(manage.getManagementStatus1())
                        .managementStatus2(manage.getManagementStatus2())
                        .managementStatus3(manage.getManagementStatus3())
                        .exitPlan1(manage.getExitPlan1())
                        .exitPlan2(manage.getExitPlan2())
                        .exitEstimation(manage.getExitEstimation())
                        .createdAt(manage.getCreatedAt())
                        .updatedAt(manage.getUpdatedAt())
                        .createdBy(manage.getCreatedBy())
                        .updatedBy(manage.getUpdatedBy())
                        .build())
                .collect(Collectors.toList());
    }
}