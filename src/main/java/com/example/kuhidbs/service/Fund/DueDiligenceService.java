package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.dueDili.CDueDiligenceDTO;
import com.example.kuhidbs.dto.Fund.dueDili.RDueDiligenceDTO;
import com.example.kuhidbs.entity.Fund.DueDiligence;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.Fund.DueDiligenceRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DueDiligenceService {

    @Autowired
    private DueDiligenceRepository dueDiligenceRepository;

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public void createDueDiligence(CDueDiligenceDTO dto) {
        Investment investment = investmentRepository.findById(dto.getInvestmentId())
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        DueDiligence dueDiligence = new DueDiligence();
        dueDiligence.setFund(investment.getFund());
        dueDiligence.setInvestment(investment);
        dueDiligence.setTarget(dto.getTarget());
        dueDiligence.setStatus(dto.getStatus());
        dueDiligence.setInspectionDate(dto.getInspectionDate());
        dueDiligence.setIssues(dto.getIssues());

        dueDiligenceRepository.save(dueDiligence);
    }

    public List<RDueDiligenceDTO> getDueDiligenceByFundId(String fundId) {
        List<DueDiligence> dueDiligences = dueDiligenceRepository.findByFund_FundId(fundId);

        return dueDiligences.stream().map(dueDiligence -> {
            Investment investment = dueDiligence.getInvestment();

            return new RDueDiligenceDTO(
                    investment.getCompany().getCompanyName(),  // 회사명
                    investment.getInvestmentDate(),
                    investment.getInvestmentProduct(),
                    investment.getInvestmentSumPrice(),
                    dueDiligence.getTarget(),
                    dueDiligence.getStatus(),
                    dueDiligence.getInspectionDate(),
                    dueDiligence.getIssues(),
                    dueDiligence.getCreatedAt(),
                    dueDiligence.getUpdatedAt(),
                    dueDiligence.getCreatedBy(),
                    dueDiligence.getUpdatedBy()
            );
        }).collect(Collectors.toList());
    }
}