package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.회수.CStcupDTO;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.entity.company.Recovery;
import com.example.kuhidbs.repository.company.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveryService {

    private final RecoveryRepository recoveryRepository;
    private final InvestmentRepository investmentRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public Recovery saveRecovery(CStcupDTO stcupDTO) {
        // Investment 객체 조회
        Investment investment = investmentRepository.findById(stcupDTO.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Investment not found with ID: " + stcupDTO.getInvestmentId()));

        Company company = companyRepository.findById(stcupDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + stcupDTO.getInvestmentId()));

        // Recovery 엔터티 생성 및 설정
        Recovery recovery = Recovery.builder()
                .investment(investment) // ManyToOne 관계 설정
                .company(company)
                .recoveryDate(stcupDTO.getRecoveryDate())
                .recoveryCount(stcupDTO.getRecoveryCount())
                .recoveryUnitPrice(stcupDTO.getRecoveryUnitPrice())
                .recoveryEquityRate(stcupDTO.getEquityRate())
                .fundReturn(stcupDTO.getFundReturn())
                .kuhReturn(stcupDTO.getKuhReturn())
                .build();

        Recovery savedRecovery = recoveryRepository.save(recovery);

        // 최신 계좌 데이터 조회
        Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(stcupDTO.getInvestmentId());
        // 일부 컬럼 수정
        Account updatedAccount = Account.builder()
                .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                .unitPrice(latestAccount.getUnitPrice()) // 기존 주당 가치 유지
                .heldShareCount(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount()) // 기존 보유주식수 - 매각주식수
                .totalPrincipal(latestAccount.getTotalPrincipal() - stcupDTO.getRecoveryCount()*latestAccount.getUnitPrice())
                .functionType("회수") // 실행 함수 업데이트()
                .kuhEquityRate(latestAccount.getKuhEquityRate()) // 기존 KUH 지분율 유지
                .build();

        accountRepository.save(updatedAccount);
        return savedRecovery;
    }


}
