package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.팁스.CTIPSDTO;
import com.example.kuhidbs.dto.company.팁스.RTIPSDTO;
import com.example.kuhidbs.dto.company.팁스.UTIPSDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.TIPS;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.TipsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<RTIPSDTO> getTipsByCompanyId(String companyId) {
        List<TIPS> tipsList = tipsRepository.findByCompany_CompanyId(companyId);

        return tipsList.stream()
                .map(tips -> RTIPSDTO.builder()
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
                        .build())
                .collect(Collectors.toList());
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
