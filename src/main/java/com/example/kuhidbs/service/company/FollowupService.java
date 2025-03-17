package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.kuh투자.CIvtDTO;
import com.example.kuhidbs.dto.company.후속투자.CFolDTO;
import com.example.kuhidbs.dto.company.후속투자.RFolDTO;
import com.example.kuhidbs.entity.CompanyAccount;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kuhidbs.service.Fund.IASService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j // Lombok을 사용한 로그 (자동으로 Logger 생성)
@Service
public class FollowupService {

    @Autowired
    private FollowupRepository followupRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyAccountRepository companyAccountRepository;
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
        log.info("📌 saveFollowup 시작 - followupDto: {}", followupDto);

        Company company = companyRepository.findById(followupDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 companyId가 존재하지 않습니다: " + followupDto.getCompanyId()));

        log.info("✔ Company 조회 성공 - companyId: {}", followupDto.getCompanyId());

        List<Investment> investments = company.getInvestments();
        Long postValue = 0L;

        for (Investment investment : investments) {
            log.debug("🔍 처리 중인 Investment - investmentId: {}", investment.getInvestmentId());

            // 가장 최신의 계좌를 가져온다
            Account account = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(investment.getInvestmentId());
            log.debug("📊 최신 Account 조회 - accountId: {}, totalShareCount: {}", account.getAccountId(), account.getTotalShareCount());

            Long curTotalShareCount = account.getTotalShareCount() + followupDto.getFollowupShareCount();
            log.debug("🔢 새로운 totalShareCount 계산: {}", curTotalShareCount);

            Account newAccount = toAccountEntity(followupDto, investment, account, curTotalShareCount);
            accountRepository.save(newAccount);

            log.info("✅ 새로운 Account 저장 - {}", newAccount);


            postValue = newAccount.getPostValue();

            Account account2 = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(investment.getInvestmentId());

            log.info("✅ 삽입할 Account 조회 - {}", account2);
            InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());
            log.debug("📈 InvestmentAssetSummary 조회 완료 - investmentId: {}", investment.getInvestmentId());

            iasService.calculateDerivedFields(ias, account2);
            log.info("📊 Derived Fields 계산 완료 - investmentId: {}", investment.getInvestmentId());
        }

        // ✅ Followup 엔티티 변환 및 저장
        Followup followup = toEntity(followupDto, postValue);
        log.info("📌 Followup 엔티티 변환 완료 - postValue: {}", postValue);

        Followup savedFollowup = followupRepository.save(followup);
        log.info("✅ Followup 저장 완료 - followupId: {}", savedFollowup.getFollowupId());

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
