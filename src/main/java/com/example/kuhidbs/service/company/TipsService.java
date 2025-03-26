package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.tips.CTIPSDTO;
import com.example.kuhidbs.dto.company.tips.RTIPSDTO;
import com.example.kuhidbs.dto.company.tips.UTIPSDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.TIPS;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.TipsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipsService {

    private final TipsRepository tipsRepository;
    private final CompanyRepository companyRepository;

    // TIPS 생성
    @Transactional
    public TIPS createTIPS(CTIPSDTO dto) {
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

        return tipsRepository.save(tips);
    }

    public RTIPSDTO getTipsByCompanyId(String companyId) {
        // 해당 회사의 TIPS 데이터를 조회
        List<TIPS> tipsList = tipsRepository.findByCompany_CompanyId(companyId);

        // 만약 TIPS 데이터가 없으면 null 반환
        if (tipsList.isEmpty()) {
            return null;
        }

        // 가장 최신(첫 번째) 데이터 반환
        TIPS tips = tipsList.getFirst(); // 또는 원하는 로직을 적용해 특정 데이터 선택

        return RTIPSDTO.builder()
                .tipsId(tips.getTipsId())
                .tipsSelectionType(tips.getTipsSelectionType())
                .selectionYesNo(tips.getSelectionYesNo())
                .tipsSelectionDate(tips.getTipsSelectionDate())
                .executionStartDate(tips.getExecutionStartDate())
                .executionEndDate(tips.getExecutionEndDate())
                .successYesNo(tips.getSuccessYesNo())
                .kips(tips.getKips())
                .tipsComment(tips.getTipsComment())
                .tipsManagementEndDate(tips.getTipsManagementEndDate())
                .followTips(tips.getFollowTips())
                .createdAt(tips.getCreatedAt())
                .updatedAt(tips.getUpdatedAt())
                .createdBy(tips.getCreatedBy())
                .updatedBy(tips.getUpdatedBy())
                .build();
    }


    public void updateTips(UTIPSDTO dto) {
        TIPS tips = tipsRepository.findById(dto.getTipsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 TIPS가 존재하지 않습니다. ID: " + dto.getTipsId()));

        // 업데이트할 필드 설정
        tips.setTipsSelectionType(dto.getTipsSelectionType());
        tips.setSelectionYesNo(dto.getSelectionYesNo());
        tips.setTipsSelectionDate(dto.getTipsSelectionDate());
        tips.setExecutionStartDate(dto.getExecutionStartDate());
        tips.setExecutionEndDate(dto.getExecutionEndDate());
        tips.setSuccessYesNo(dto.getSuccessYesNo());
        tips.setKips(dto.getKips());
        tips.setTipsComment(dto.getTipsComment());
        tips.setTipsManagementEndDate(dto.getTipsManagementEndDate());
        tips.setFollowTips(dto.getFollowTips());

        tipsRepository.save(tips); // 변경 사항 저장
    }
}
