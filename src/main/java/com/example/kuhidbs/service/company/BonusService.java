package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.무증.CBonusDTO;
import com.example.kuhidbs.dto.company.무증.RBonusDTO;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Bonus;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.BonusRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;
    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;

    // 무상증자 생성
    @Transactional
    public Bonus createBonus(CBonusDTO dto) {
        // 투자 고유 번호로 Ivt 객체 조회
        Investment investment = investmentRepository.findById(dto.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + dto.getInvestmentId()));        // 투자 고유 번호로 Ivt 객체 조회
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + dto.getInvestmentId()));


        // 최신 계좌 데이터 조회
        Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(dto.getInvestmentId());
        Long newUnitValue = latestAccount.getUnitPrice()*latestAccount.getHeldShareCount()/dto.getChangedShareCount();
                // DTO → Entity 변환 후 저장
                Bonus bonus = Bonus.builder()
                .company(company)
                .investment(investment)
                .bonusDate(dto.getBonusDate())
                .changedShareCount(dto.getChangedShareCount())
                .unitPrice(newUnitValue)
                .build();

        // 일부 컬럼 수정
        Account updatedAccount = Account.builder()
                .accountDate(dto.getBonusDate()) //날짜갱신
                .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                .unitPrice(newUnitValue) // 새로운 주당 가치로 업데이트
                .heldShareCount(dto.getChangedShareCount()) // 기존 보유 주식 수 변경
                .totalPrincipal(latestAccount.getTotalPrincipal()) // 기존 투자 원금 유지
                .functionType("무상증자") // 실행 함수 업데이트
                .kuhEquityRate(latestAccount.getKuhEquityRate()) // 기존 KUH 지분율 유지
                .curUnitPrice(latestAccount.getCurUnitPrice()*newUnitValue/latestAccount.getUnitPrice()) // 현재단가
                .totalShareCount(latestAccount.getTotalShareCount()*dto.getChangedShareCount()/latestAccount.getHeldShareCount())//발행총주식수
                .postValue(latestAccount.getPostValue())//현재시총
                .build();

        accountRepository.save(updatedAccount);

        return bonusRepository.save(bonus);
    }

    /**
     * 특정 기업의 모든 무상증자 내역 조회
     */
    @Transactional(readOnly = true)
    public List<RBonusDTO> getAllBonusByCompanyId(String companyId) {
        List<Bonus> bonuses = bonusRepository.findByCompany_CompanyId(companyId);

        return bonuses.stream()
                .map(bonus -> new RBonusDTO(
                        bonus.getBonusDate(),
                        bonus.getChangedShareCount(),
                        bonus.getUnitPrice()
                ))
                .collect(Collectors.toList());
    }
}
