package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.액분.CStockSplitDto;
import com.example.kuhidbs.dto.company.후속투자.CFolDTO;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.entity.company.StockSplit;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.StockSplitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StockSplitService {
    private static final Logger logger = LoggerFactory.getLogger(StockSplitService.class);

    private final StockSplitRepository stockSplitRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    private Account toAccountEntity(CStockSplitDto dto, Investment savedInvestment, Account lastAccount) {
        logger.info("계좌 데이터 변환 시작: InvestmentId={}, LastAccountId={}",
                savedInvestment.getInvestmentId(), lastAccount.getAccountId());

        Account newAccount = Account.builder()
                .investment(savedInvestment)
                .accountDate(dto.getSplitDate())
                .unitPrice(dto.getPostSplitUnitPrice())
                .heldShareCount((long) (lastAccount.getHeldShareCount() * dto.getSplitRatio()))
                .totalPrincipal(lastAccount.getTotalPrincipal())
                .functionType("액면분할")
                .curUnitPrice(BigDecimal.valueOf(lastAccount.getCurUnitPrice())
                        .divide(BigDecimal.valueOf(dto.getSplitRatio()), RoundingMode.HALF_UP)
                        .longValue())
                .totalShareCount((long) (lastAccount.getTotalShareCount() * dto.getSplitRatio()))
                .postValue(lastAccount.getPostValue())
                .kuhEquityRate(lastAccount.getKuhEquityRate())
                .build();

        logger.info("새 계좌 데이터 변환 완료: NewAccount={}", newAccount);
        return newAccount;
    }

    public StockSplit createStockSplit(CStockSplitDto stockSplitDto) {
        logger.info("액면분할 생성 요청: {}", stockSplitDto);

        Company company = companyRepository.findById(stockSplitDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 companyId가 존재하지 않습니다: " + stockSplitDto.getCompanyId()));

        logger.info("기업 정보 조회 완료: CompanyId={}, CompanyName={}", company.getCompanyId(), company.getCompanyName());

        List<Investment> investments = company.getInvestments();
        logger.info("기업의 투자 정보 조회 완료: InvestmentCount={}", investments.size());

        // Investment 리스트를 반복하면서 처리
        for (Investment investment : investments) {
            logger.info("투자 정보 처리 중: InvestmentId={}", investment.getInvestmentId());

            // 가장 최신의 계좌를 가져옴
            Account account = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(investment.getInvestmentId());
            logger.info("최신 계좌 조회 완료: AccountId={}, HeldShareCount={}", account.getAccountId(), account.getHeldShareCount());

            // 새로운 계좌 생성 후 저장
            Account newAccount = toAccountEntity(stockSplitDto, investment, account);
            accountRepository.save(newAccount);
            logger.info("새 계좌 저장 완료: NewAccountId={}", newAccount.getAccountId());
        }

        // StockSplit 생성 및 저장
        StockSplit stockSplit = new StockSplit();
        stockSplit.setCompany(company);
        stockSplit.setSplitDate(stockSplitDto.getSplitDate());
        stockSplit.setPreSplitUnitPrice(stockSplitDto.getPreSplitUnitPrice());
        stockSplit.setPostSplitUnitPrice(stockSplitDto.getPostSplitUnitPrice());
        stockSplit.setSplitRatio(stockSplitDto.getSplitRatio());

        StockSplit savedStockSplit = stockSplitRepository.save(stockSplit);
        logger.info("액면분할 저장 완료: StockSplitId={}", savedStockSplit.getStockSplitId());

        return savedStockSplit;
    }
}

