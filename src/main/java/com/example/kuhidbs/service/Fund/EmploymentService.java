package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.REmploymentDTO;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.repository.Fund.EmploymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmploymentService {

    private final EmploymentRepository employmentRepository;

    @Autowired
    public EmploymentService(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    /**
     * 펀드 ID로 Employment 리스트 조회 및 DTO 변환
     */
    public List<REmploymentDTO> showAllEmployment(String fundId) {
        List<Employment> employmentList = employmentRepository.findByFundId(fundId);

        return employmentList.stream()
                .map(employment -> new REmploymentDTO(
                        employment.getInvestment().getInvestmentId(),
                        employment.getInvestment().getCompany().getCompanyName(),
                        employment.getInitialInvestmentDate(),
                        employment.getInitialEmployeeCount(),
                        employment.getLatestEmployeeCount(),
                        employment.getFinalRecoveryDate(),
                        employment.getFinalEmployeeCount(),
                        employment.getFinancialYear(),
                        employment.getFinancialHalf()
                ))
                .collect(Collectors.toList());
    }
}
