package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.CAuditDTO;
import com.example.kuhidbs.dto.Fund.CFundDTO;
import com.example.kuhidbs.dto.Fund.RAuditDTO;
import com.example.kuhidbs.entity.Fund.*;
import com.example.kuhidbs.repository.Fund.AuditRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .fncYear(dto.getFncYear())
                .changeDate(dto.getChangeDate())
                .auditorName(dto.getAuditorName())
                .changeReason(dto.getChangeReason())
                .build();

        return auditRepository.save(audit);
    }

    // 회계감사 정보 생성
    public void createAuditByFund(CFundDTO dto) {
        Fund fund = fundRepository.findById(dto.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 조합 ID: " + dto.getFundId()));

        Audit audit = Audit.builder()
                .fund(fund)
                .fncYear(dto.getDurationStartDate().substring(0, 4))
                .changeDate(dto.getAuditorDate())
                .auditorName(dto.getAuditorName())
                .changeReason("최초선임")
                .build();

        auditRepository.save(audit);
    }

    // 특정 fundId에 해당하는 모든 Audit 데이터 조회
    public List<RAuditDTO> getAuditsByFundId(String fundId) {
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 조합 ID: " + fundId));

        List<Audit> audits = auditRepository.findByFund(fund);

        return audits.stream()
                .map(audit -> RAuditDTO.builder()
                        .auditId(audit.getAuditId())
                        .fncYear(audit.getFncYear())
                        .changeDate(audit.getChangeDate())
                        .auditorName(audit.getAuditorName())
                        .changeReason(audit.getChangeReason())
                        .build())
                .collect(Collectors.toList());
    }
}
