package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.kuh투자.CIvtDTO;
import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.entity.CompanyAccount;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.Fund.EmploymentRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import com.example.kuhidbs.service.Fund.IASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private EmploymentRepository EmploymentRepository;
    @Autowired
    private EmploymentRepository employmentRepository;

    /**
     * 투자 정보를 저장하는 메서드.
     *
     * @param dto CIvtDTO 객체
     * @return 저장된 Investment 엔터티
     */
    public Investment saveInvestment(CIvtDTO dto) {
        try {
            // 1. Company 객체 조회
            System.out.println("[INFO] 회사 조회 시작: companyId = " + dto.getCompanyId());
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] 유효하지 않은 회사 ID: " + dto.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + dto.getCompanyId());
                    });
            System.out.println("[INFO] 회사 조회 성공: " + company.getCompanyName());

            // 2. Fund 객체 조회
            System.out.println("[INFO] 펀드 조회 시작: fundId = " + dto.getFundId());
            Fund fund = fundRepository.findById(dto.getFundId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] 유효하지 않은 펀드 ID: " + dto.getFundId());
                        return new IllegalArgumentException("Invalid fund ID: " + dto.getFundId());
                    });
            System.out.println("[INFO] 펀드 조회 성공: " + fund.getFundName());

            // 3. DTO를 Investment 엔터티로 변환 및 저장
            System.out.println("[INFO] 투자 데이터 변환 및 저장 시작");
            Investment investment = toEntity(dto, company, fund);
            Investment savedInvestment = investmentRepository.save(investment);
            System.out.println("[INFO] 투자 데이터 저장 완료: investmentId = " + savedInvestment.getInvestmentId());

            // 4. Account 엔터티 생성 및 저장
            System.out.println("[INFO] 계좌 데이터 변환 및 저장 시작");
            Account account = toAccountEntity(dto, savedInvestment);
            accountRepository.save(account);
            System.out.println("[INFO] 계좌 데이터 저장 완료: accountId = " + account.getAccountId());

            // 5. 투자자산총괄데이터(InvestmentAssetSummary) 생성
            System.out.println("[INFO] 투자자산총괄 데이터 생성 시작");
            InvestmentAssetSummary assetSummary = InvestmentAssetSummary.builder()
                    .fund(fund)
                    .investment(savedInvestment)
                    .investmentProduct(dto.getInvestmentProduct())
                    .investmentAmount(dto.getInvestmentSumPrice())
                    .investmentDate(dto.getInvestmentDate())
                    .investmentCompany(company.getCompanyName())
                    .evaluationMethod(dto.getEvaluationMethod())
                    .remainingAssetValuation(dto.getInvestmentSumPrice())
                    .managementFeeTarget(dto.getManagementFeeTarget())
                    .build();

            // 파생 필드 계산 및 저장
            System.out.println("[INFO] 투자자산총괄 데이터 파생 필드 계산 시작");
            iasService.calculateDerivedFields(assetSummary, null);
            investmentAssetSummaryRepository.save(assetSummary);
            System.out.println("[INFO] 투자자산총괄 데이터 저장 완료: assetSummaryId = " + assetSummary.getInvestmentAssetSummaryId());

            // 6. 고용인력 데이터 생성
            System.out.println("[INFO] 고용인력 데이터 생성 시작");
            Employment employment = Employment.builder()
                    .investment(savedInvestment) // 투자 정보
                    .companyNm(company.getCompanyName()) //투자기업
                    .initialInvestmentDate(dto.getInvestmentDate()) // 최초 투자일자
                    .initialEmployeeCount(company.getEmployeeCount()) // 최초 투자 시점 인력 수
                    .latestEmployeeCount(null) // 최신 인력 수 (최초에는 null)
                    .finalRecoveryDate(null) // 최종 회수일자 (아직 없음)
                    .finalEmployeeCount(null) // 최종 회수 시점 인력 수 (기본값)
                    .build();
            employmentRepository.save(employment);
            System.out.println("[INFO] 고용인력 데이터 저장 완료: employmentId = " + employment.getId());

            return savedInvestment;

        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 입력 데이터 오류 발생: " + e.getMessage());
            throw e; // 예외 다시 던짐 (트랜잭션 롤백)
        } catch (DataAccessException e) {
            System.out.println("[ERROR] 데이터베이스 접근 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("Database error occurred", e);
        } catch (Exception e) {
            System.out.println("[ERROR] 알 수 없는 오류 발생: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred", e);
        }
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
                .managementFeeTarget(investment.getManagementFeeTarget())
                .evaluationMethod(investment.getEvaluationMethod())
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
                        .managementFeeTarget(investment.getManagementFeeTarget())
                        .evaluationMethod(investment.getEvaluationMethod())
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
                .managementFeeTarget(dto.getManagementFeeTarget()) //관리보수대상여부
                .evaluationMethod(dto.getEvaluationMethod()) //평가방법
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
