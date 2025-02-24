package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.회수.CStcupDTO;
import com.example.kuhidbs.dto.company.회수.RstcupDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.entity.company.Recovery;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.*;
import com.example.kuhidbs.service.Fund.IASService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Recovery saveRecovery(CStcupDTO stcupDTO) {
        // Investment 객체 조회
        Investment investment = investmentRepository.findById(stcupDTO.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Investment not found with ID: " + stcupDTO.getInvestmentId()));

        Company company = companyRepository.findById(stcupDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + stcupDTO.getInvestmentId()));

        Long fundReturn = (stcupDTO.getRecoveryUnitPrice() - investment.getInvestmentUnitPrice())* stcupDTO.getRecoveryCount();
        Long recoveryReturn;
        if(!Objects.equals(investment.getFund().getFundId(), "고유계정"))
        {
            //고유계정인 경우와 아닌경우를 구분해야
            recoveryReturn = BigDecimal.valueOf(fundReturn)
                    .multiply(investment.getFund().getAllocRatio())
                    .setScale(0, RoundingMode.HALF_UP) // 소수점 첫째 자리에서 반올림
                    .longValue();
        }
        else{
            recoveryReturn = fundReturn;
        }



        // Recovery 엔터티 생성 및 설정
        Recovery recovery = Recovery.builder()
                .investment(investment) // ManyToOne 관계 설정
                .company(company)
                .recoveryDate(stcupDTO.getRecoveryDate())
                .recoveryCount(stcupDTO.getRecoveryCount())
                .recoveryUnitPrice(stcupDTO.getRecoveryUnitPrice())
                .fundReturn(recoveryReturn)
                .kuhReturn(recoveryReturn)
                .build();

        Recovery savedRecovery = recoveryRepository.save(recovery);



        // 최신 계좌 데이터 조회
        Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(stcupDTO.getInvestmentId());
        // 일부 컬럼 수정
        Account updatedAccount = Account.builder()
                .accountDate(stcupDTO.getRecoveryDate())
                .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                .unitPrice(latestAccount.getUnitPrice()) // 기존 주당 가치 유지
                .heldShareCount(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount()) // 기존 보유주식수 - 매각주식수
                .totalPrincipal(latestAccount.getTotalPrincipal() - stcupDTO.getRecoveryCount()*latestAccount.getUnitPrice())
                .functionType("회수") // 실행 함수 업데이트()
                .curUnitPrice(latestAccount.getCurUnitPrice()) //현재단가도 유지
                .postValue(latestAccount.getPostValue()) //기존 시총 유지
                .totalShareCount(latestAccount.getTotalShareCount()) //발행총주식수도 유지
                .kuhEquityRate(
                        BigDecimal.valueOf(latestAccount.getHeldShareCount() - stcupDTO.getRecoveryCount())
                                .multiply(BigDecimal.valueOf(100)) // 백분율 변환
                                .divide(BigDecimal.valueOf(latestAccount.getTotalShareCount()), 2, RoundingMode.HALF_UP) // 소수점 2자리 반올림
                )  //kuh지분율 갱신
                .build();



        accountRepository.save(updatedAccount);

        InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());

        //회수원금
        Long recoveredPrincipal = stcupDTO.getRecoveryCount()*investment.getInvestmentUnitPrice();

        //회수수익
        Long recoveredProfit = (stcupDTO.getRecoveryUnitPrice() - investment.getInvestmentUnitPrice())*stcupDTO.getRecoveryCount();

        ias.setRecoveredPrincipal(recoveredPrincipal);
        ias.setRecoveredProfit(recoveredProfit);
        //잔여자산
        Long remainingAssetValuation = recoveredPrincipal* latestAccount.getCurUnitPrice()/latestAccount.getUnitPrice();
        ias.setRemainingAssetValuation(remainingAssetValuation);

        iasService.calculateDerivedFields(ias);
        investmentAssetSummaryRepository.save(ias);

        return savedRecovery;
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
