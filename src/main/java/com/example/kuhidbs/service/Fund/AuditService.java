package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.Fund.CAuditDTO;
import com.example.kuhidbs.entity.Audit;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.repository.Fund.AuditRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;
    private final FundRepository fundRepository;

    // 회계감사 정보 생성
    public Audit createAudit(CAuditDTO dto) {
        Fund fund = fundRepository.findById(dto.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 조합 ID: " + dto.getFundId()));

        Audit audit = Audit.builder()
                .fund(fund)
                .changeDate(dto.getChangeDate())
                .auditorName(dto.getAuditorName())
                .changeReason(dto.getChangeReason())
                .build();

        return auditRepository.save(audit);
    }
}
