package com.example.kuhidbs.service.company;

import ch.qos.logback.classic.Logger;
import com.example.kuhidbs.dto.company.followup.CFolDTO;
import com.example.kuhidbs.dto.company.followup.RFolDTO;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Followup;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.FollowupRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kuhidbs.service.Fund.IASService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j // Lombok을 사용한 로그 (자동으로 Logger 생성)
@Service
public class FollowupService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(FollowupService.class);

    @Autowired
    private FollowupRepository followupRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InvestmentAssetSummaryRepository investmentAssetSummaryRepository;
    @Autowired
    private IASService iasService;

    private Account toAccountEntity(CFolDTO dto, Investment savedInvestment, Account lastAccount, Long curTotalShareCount) {
        return Account.builder()
                .investment(savedInvestment) // Ivt 엔터티 설정 (ManyToOne 관계)
                .accountDate(dto.getFollowupStartDate()) //날짜
                .unitPrice(lastAccount.getUnitPrice()) // 투자 단가
                .heldShareCount(lastAccount.getHeldShareCount()) // 보유 주식 수량
                .totalPrincipal(lastAccount.getTotalPrincipal()) // 투자 원금 (투자 금액)
                .functionType("후속투자") // 실행 함수 (예: "SAVE_INVESTMENT")
                .curUnitPrice(dto.getFollowupUnitPrice()) // 현재단가
                .totalShareCount(curTotalShareCount)//발행총주식수
                .postValue(dto.getFollowupUnitPrice()*curTotalShareCount)//현재시총
                .kuhEquityRate(
                        BigDecimal.valueOf(lastAccount.getHeldShareCount())
                                .multiply(BigDecimal.valueOf(100)) // 백분율 변환
                                .divide(BigDecimal.valueOf(curTotalShareCount), 2, RoundingMode.HALF_UP) // 소수점 2자리 반올림
                )
                .build();
    }
    @Transactional
    public Followup saveFollowup(CFolDTO followupDto) {
        logger.info("Start saveFollowup - DTO: {}", followupDto);

        Company company = companyRepository.findById(followupDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + followupDto.getCompanyId()));

        logger.info("Company found - companyId: {}", followupDto.getCompanyId());

        List<Investment> investments = company.getInvestments();
        Long postValue = 0L;

        for (Investment investment : investments) {
            logger.debug("Processing investment - investmentId: {}", investment.getInvestmentId());

            // 가장 최신의 계좌를 가져온다
            Account account = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(investment.getInvestmentId());
            logger.debug("Latest account found - accountId: {}, totalShareCount: {}", account.getAccountId(), account.getTotalShareCount());

            Long curTotalShareCount = account.getTotalShareCount() + followupDto.getFollowupShareCount();
            logger.debug("New totalShareCount calculated: {}", curTotalShareCount);

            Account newAccount = toAccountEntity(followupDto, investment, account, curTotalShareCount);
            accountRepository.save(newAccount);
            logger.info("New account saved - accountId: {}", newAccount.getAccountId());

            postValue = newAccount.getPostValue();

            Account account2 = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(investment.getInvestmentId());
            logger.info("Fetched latest account for summary - accountId: {}", account2.getAccountId());

            InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());
            logger.debug("Investment asset summary found - investmentId: {}", investment.getInvestmentId());

            iasService.calculateDerivedFields(ias, account2);
            logger.info("Derived fields updated - investmentId: {}", investment.getInvestmentId());
        }

        Followup followup = toEntity(followupDto, postValue);
        logger.info("Followup entity created - postValue: {}", postValue);

        Followup savedFollowup = followupRepository.save(followup);
        logger.info("Followup saved - followupId: {}", savedFollowup.getFollowupId());

        return savedFollowup;
    }


    //postvalue 가져오는 메소드
    @Transactional(readOnly = true)
    public Long getCurrentCompanyValue(String companyId) {
        return followupRepository.findTopByCompany_CompanyIdOrderByFollowupStartDateDesc(companyId)
                .map(Followup::getFollowupInvestmentValue)
                .orElse(null); // 최신 투자 밸류가 없으면 null 반환
    }

    // 회사 ID를 기준으로 후속 투자 정보 조회
    public List<RFolDTO> getFollowupByCompanyId(String companyId) {
        List<Followup> followups = followupRepository.findByCompany_CompanyId(companyId);

        // Entity -> DTO 변환
        return followups.stream()
                .map(followup -> RFolDTO.builder()
                        .followupStartDate(followup.getFollowupStartDate())
                        .followupCompanyName(followup.getFollowupCompanyName())
                        .followupProduct(followup.getFollowupProduct())
                        .followupSumPrice(followup.getFollowupSumPrice())
                        .followupShareCount(followup.getFollowupShareCount())
                        .followupUnitPrice(followup.getFollowupUnitPrice())
                        .followupInvestmentValue(followup.getFollowupInvestmentValue())
                        .createdAt(followup.getCreatedAt())
                        .updatedAt(followup.getUpdatedAt())
                        .createdBy(followup.getCreatedBy())
                        .updatedBy(followup.getUpdatedBy())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * CFolDTO를 Followup 엔터티로 변환하는 메서드.
     *
     * @param dto 변환할 DTO 객체
     * @return 변환된 Followup 엔터티
     */
    private Followup toEntity(CFolDTO dto, Long postValue) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + dto.getCompanyId()));

        return Followup.builder()
                .company(company) // ManyToOne 관계로 설정
                .followupStartDate(dto.getFollowupStartDate())
                .followupCompanyName(dto.getFollowupCompanyName())
                .followupProduct(dto.getFollowupProduct())
                .followupSumPrice(dto.getFollowupSumPrice())
                .followupShareCount(dto.getFollowupShareCount())
                .followupUnitPrice(dto.getFollowupUnitPrice())
                .followupInvestmentValue(postValue)
                .build();
    }



}
