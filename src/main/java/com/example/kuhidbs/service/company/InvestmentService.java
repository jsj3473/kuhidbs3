package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.kuh투자.CIvtDTO;
import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.entity.CompanyAccount;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import com.example.kuhidbs.service.Fund.IASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private InvestmentAssetSummaryRepository investmentAssetSummaryRepository;

    @Autowired
    private IASService iasService;

    @Autowired
    private CompanyAccountRepository companyAccountRepository;

    /**
     * 투자 정보를 저장하는 메서드.
     *
     * @param dto CIvtDTO 객체
     * @return 저장된 Investment 엔터티
     */
    public Investment saveInvestment(CIvtDTO dto) {

        // 1. Company 객체 조회
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + dto.getCompanyId()));

        // 2. Fund 객체 조회
        Fund fund = fundRepository.findById(dto.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid fund ID: " + dto.getFundId()));

        // 3. DTO를 Investment 엔터티로 변환 및 저장
        Investment investment = toEntity(dto, company, fund);
        Investment savedInvestment = investmentRepository.save(investment);

        // 4. Account 엔터티 생성 및 저장
        Account account = toAccountEntity(dto, savedInvestment);
        accountRepository.save(account);

        // 5. 투자자산총괄데이터(InvestmentAssetSummary) 생성
        InvestmentAssetSummary assetSummary = InvestmentAssetSummary.builder()
                .fund(fund)
                .investment(savedInvestment)
                .investmentProduct(dto.getInvestmentProduct())
                .investmentAmount(dto.getInvestmentSumPrice())
                .investmentDate(dto.getInvestmentDate())
                .investmentCompany(company.getCompanyName())
                .build();

        // 6. 투자자산총괄데이터 저장
        iasService.calculateDerivedFields(assetSummary);
        investmentAssetSummaryRepository.save(assetSummary);

        //7. 회사계좌업데이트
        //CompanyAccount ca = companyAccountRepository.findByCompanyId(company.getCompanyId())
         //       .orElseGet(() -> new CompanyAccount());


        return savedInvestment;
    }


    @Transactional(readOnly = true)
    public RIvtDTO getInvestmentByCompanyIdRecent(String companyId) {
        Optional<Investment> investmentOpt = investmentRepository.findFirstByCompany_CompanyIdOrderByInvestmentIdDesc(companyId);


        return investmentOpt.map(investment -> RIvtDTO.builder()
                .investmentId(investment.getInvestmentId())
                .investmentDate(investment.getInvestmentDate())
                .investmentProduct(investment.getInvestmentProduct())
                .fundId(investment.getFund().getFundId())  // ✅ Fund 객체에서 fundId 가져오기
                .investmentSumPrice(investment.getInvestmentSumPrice())
                .investmentUnitPrice(investment.getInvestmentUnitPrice())
                .shareCount(investment.getShareCount())
                .equityRate(investment.getEquityRate())
                .totalShares(investment.getTotalShares())
                .investmentValue(investment.getInvestmentValue())
                .tangibleInvestment(investment.getTangibleInvestment())
                .investmentEmployee(investment.getInvestmentEmployee())
                .investmentStep(investment.getInvestmentStep())
                .build()).orElse(null);

    }

    @Transactional(readOnly = true)
    public List<RIvtDTO> getAllInvestmentsByCompanyId(String companyId) {
        List<Investment> investments = investmentRepository.findByCompany_CompanyId(companyId);

        return investments.stream()
                .map(investment -> RIvtDTO.builder()
                        .investmentId(investment.getInvestmentId())
                        .investmentDate(investment.getInvestmentDate())
                        .investmentProduct(investment.getInvestmentProduct())
                        .fundId(investment.getFund().getFundId())  // ✅ Fund 객체에서 fundId 가져오기
                        .investmentSumPrice(investment.getInvestmentSumPrice())
                        .investmentUnitPrice(investment.getInvestmentUnitPrice())
                        .shareCount(investment.getShareCount())
                        .equityRate(investment.getEquityRate())
                        .totalShares(investment.getTotalShares())
                        .investmentValue(investment.getInvestmentValue())
                        .tangibleInvestment(investment.getTangibleInvestment())
                        .investmentEmployee(investment.getInvestmentEmployee())
                        .investmentStep(investment.getInvestmentStep())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * CIvtDTO를 Investment 엔터티로 변환하는 메서드.
     *
     * @param dto 변환할 DTO 객체
     * @return 변환된 Investment 엔터티
     */
    private Investment toEntity(CIvtDTO dto, Company company, Fund fund) {
        return Investment.builder()
                .fund(fund) // 투자 재원
                .company(company) // 회사 고유 번호
                .investmentProduct(dto.getInvestmentProduct()) // 투자 상품
                .investmentSumPrice(dto.getInvestmentSumPrice()) // 투자 금액
                .shareCount(dto.getShareCount()) // 주식 수량
                .investmentUnitPrice(dto.getInvestmentUnitPrice()) // 투자 단가
                .equityRate(dto.getEquityRate()) // 지분율
                .investmentValue(dto.getInvestmentValue()) // 투자 밸류
                .tangibleInvestment(dto.getTangibleInvestment()) // 현물 투자
                .investmentStep(dto.getInvestmentStep()) // 투자 단계
                .investmentDate(dto.getInvestmentDate()) // 투자 일자
                .investmentEmployee(dto.getInvestmentEmployee()) // 투자 담당자
                .totalShares(dto.getTotalShares()) // 발행 주식 수
                .build();
    }

    private Account toAccountEntity(CIvtDTO dto, Investment savedInvestment) {
        return Account.builder()
                .investment(savedInvestment) // Ivt 엔터티 설정 (ManyToOne 관계)
                .accountDate(dto.getInvestmentDate()) //날짜
                .unitPrice(dto.getInvestmentUnitPrice()) // 투자 단가
                .heldShareCount(dto.getShareCount()) // 보유 주식 수량
                .totalPrincipal(dto.getInvestmentSumPrice()) // 투자 원금 (투자 금액)
                .functionType("최초투자") // 실행 함수 (예: "SAVE_INVESTMENT")
                .curUnitPrice(dto.getInvestmentUnitPrice()) // 현재단가
                .totalShareCount(dto.getTotalShares())//발행총주식수
                .postValue(dto.getInvestmentValue())//현재시총
                .kuhEquityRate(dto.getEquityRate()) // KUH 지분율
                .build();
    }


}
