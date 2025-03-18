package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.recovery.CStcupDTO;
import com.example.kuhidbs.dto.company.recovery.RstcupDTO;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.entity.company.Recovery;
import com.example.kuhidbs.repository.Fund.EmploymentRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.*;
import com.example.kuhidbs.service.Fund.IASService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecoveryService {

    private final RecoveryRepository recoveryRepository;
    private final InvestmentRepository investmentRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final InvestmentAssetSummaryRepository investmentAssetSummaryRepository;
    private final IASService iasService;
    private final FundRepository fundRepository;
    private final EmploymentRepository employmentRepository;


    public void saveRecovery(CStcupDTO stcupDTO) {
        System.out.println("🔹 saveRecovery() 시작 - DTO: " + stcupDTO);

        try {
            // Investment 객체 조회
            Investment investment = investmentRepository.findById(stcupDTO.getInvestmentId())
                    .orElseThrow(() -> {
                        System.out.println("❌ Investment not found with ID: " + stcupDTO.getInvestmentId());
                        return new IllegalArgumentException("Investment not found with ID: " + stcupDTO.getInvestmentId());
                    });

            // Company 객체 조회
            Company company = companyRepository.findById(stcupDTO.getCompanyId())
                    .orElseThrow(() -> {
                        System.out.println("❌ Invalid company ID: " + stcupDTO.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + stcupDTO.getCompanyId());
                    });

            System.out.println("✅ Investment & Company 조회 완료");

            Long fundReturn = (stcupDTO.getRecoveryUnitPrice() - investment.getInvestmentUnitPrice()) * stcupDTO.getRecoveryCount();
            Long recoveryReturn;

            if (!Objects.equals(investment.getFund().getFundId(), "고유계정")) {
                // 고유계정 아닌 경우 계산
                recoveryReturn = BigDecimal.valueOf(fundReturn)
                        .multiply(investment.getFund().getIvtRatio())
                        .setScale(0, RoundingMode.HALF_UP)
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                        .longValue();


            } else {
                recoveryReturn = fundReturn;
            }

            System.out.println("✅ Recovery 계산 완료: recoveryReturn = " + recoveryReturn);
            // Recovery 엔터티 생성
            Recovery recovery = Recovery.builder()
                    .investment(investment)
                    .company(company)
                    .recoveryDate(stcupDTO.getRecoveryDate())
                    .recoveryCount(stcupDTO.getRecoveryCount())
                    .recoveryUnitPrice(stcupDTO.getRecoveryUnitPrice())
                    .fundReturn(fundReturn)
                    .kuhReturn(recoveryReturn)
                    .build();

            Recovery savedRecovery = recoveryRepository.save(recovery);
            System.out.println("✅ Recovery 저장 완료 - ID: " + savedRecovery.getRecoveryId());

            // 최신 계좌 데이터 조회
            Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(stcupDTO.getInvestmentId());
            if (latestAccount == null) {
                System.out.println("⚠️ 계좌 데이터가 존재하지 않음: 투자 ID " + stcupDTO.getInvestmentId());
                throw new IllegalStateException("계좌 데이터가 존재하지 않습니다.");
            }

            System.out.println("✅ 최신 계좌 조회 완료");

            // 계좌 데이터 업데이트
            Account updatedAccount = Account.builder()
                    .accountDate(stcupDTO.getRecoveryDate())
                    .investment(latestAccount.getInvestment())
                    .unitPrice(latestAccount.getUnitPrice())
                    .heldShareCount(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount())
                    .totalPrincipal(latestAccount.getTotalPrincipal() - (stcupDTO.getRecoveryCount() * latestAccount.getUnitPrice()))
                    .functionType("회수")
                    .curUnitPrice(latestAccount.getCurUnitPrice())
                    .postValue(latestAccount.getPostValue())
                    .totalShareCount(latestAccount.getTotalShareCount())
                    .kuhEquityRate(
                            BigDecimal.valueOf(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount())
                                    .multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(latestAccount.getTotalShareCount()), 2, RoundingMode.HALF_UP)
                    )
                    .build();

            accountRepository.save(updatedAccount);
            System.out.println("✅ 계좌 데이터 업데이트 완료");

            //회수후 보유주식수가 0 이면 employment 테이블
            if(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount() == 0) {
                // 기존 Employment 데이터 조회
                Employment employment = employmentRepository.findByInvestment(investment);
                employment.setFinalRecoveryDate(stcupDTO.getRecoveryDate());
                employment.setLatestEmployeeCount(10);
                employmentRepository.save(employment);
            }
            // 투자 자산 요약 조회
            InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());
            if (ias == null) {
                System.out.println("⚠️ InvestmentAssetSummary 데이터 없음: 투자 ID " + investment.getInvestmentId());
                throw new IllegalStateException("InvestmentAssetSummary 데이터를 찾을 수 없습니다.");
            }

            System.out.println("✅ 투자 자산 요약 데이터 조회 완료");

            // 회수 원금 및 수익 업데이트
            Long recoveredPrincipal = stcupDTO.getRecoveryCount() * investment.getInvestmentUnitPrice();
            Long recoveredProfit = (stcupDTO.getRecoveryUnitPrice() - investment.getInvestmentUnitPrice()) * stcupDTO.getRecoveryCount();

            ias.setRecoveredPrincipal(ias.getRecoveredPrincipal()+recoveredPrincipal);
            ias.setRecoveredProfit(ias.getRecoveredProfit()+recoveredProfit);
            investmentAssetSummaryRepository.save(ias);
            iasService.updateFundAllocation(investment.getFund().getFundId());

            System.out.println("✅ 회수 데이터 저장 완료 - 원금: " + recoveredPrincipal + ", 수익: " + recoveredProfit);

            // 최종 계좌 데이터 업데이트
            Account latestAccount2 = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(stcupDTO.getInvestmentId());
            iasService.calculateDerivedFields(ias, latestAccount2);

            System.out.println("🚀 회수 프로세스 완료");

        } catch (Exception e) {
            System.out.println("❌ saveRecovery() 중 오류 발생: " + e.getMessage());
            e.printStackTrace();  // 전체 스택 트레이스 출력
            throw new RuntimeException("회수 데이터 저장 중 오류 발생", e);
        }
    }



    /**
     * 특정 기업의 모든 회수 내역 조회
     */
    @Transactional(readOnly = true)
    public List<RstcupDTO> getAllstcupByCompanyId(String companyId) {
        List<Recovery> recoveries = recoveryRepository.findByCompany_CompanyId(companyId);

        return recoveries.stream()
                .map(recovery -> new RstcupDTO(
                        recovery.getCompany().getCompanyId(),
                        recovery.getInvestment().getInvestmentId(),
                        recovery.getRecoveryDate(),
                        recovery.getRecoveryCount(),
                        recovery.getRecoveryUnitPrice(),
                        recovery.getFundReturn(),
                        recovery.getKuhReturn()
                ))
                .collect(Collectors.toList());
    }
}
