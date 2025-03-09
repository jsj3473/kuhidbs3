package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.kuh투자.CIvtDTO;
import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.entity.CompanyAccount;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.Fund.EmploymentRepository;
import com.example.kuhidbs.repository.Fund.FundAchievementRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import com.example.kuhidbs.service.Fund.IASService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class InvestmentService {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);

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
    private FundAchievementRepository fundAchievementRepository;

    @Autowired
    private IASService iasService;

    @Autowired
    private EmploymentRepository employmentRepository;

    /**
     * 투자 정보를 저장하는 메서드.
     *
     * @param dto CIvtDTO 객체
     * @return 저장된 Investment 엔터티
     */
    @Transactional
    public Investment saveInvestment(CIvtDTO dto) {
        try {
            // 1. Company 객체 조회
            logger.info("회사 조회 시작: companyId = {}", dto.getCompanyId());
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> {
                        logger.error("유효하지 않은 회사 ID: {}", dto.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + dto.getCompanyId());
                    });
            logger.info("회사 조회 성공: {}", company.getCompanyName());

            // 2. Fund 객체 조회
            logger.info("펀드 조회 시작: fundId = {}", dto.getFundId());
            Fund fund = fundRepository.findById(dto.getFundId())
                    .orElseThrow(() -> {
                        logger.error("유효하지 않은 펀드 ID: {}", dto.getFundId());
                        return new IllegalArgumentException("Invalid fund ID: " + dto.getFundId());
                    });
            logger.info("펀드 조회 성공: {}", fund.getFundName());

            // 3. DTO를 Investment 엔티티로 변환 및 저장
            logger.info("투자 데이터 변환 및 저장 시작");
            Investment investment = toEntity(dto, company, fund);
            Investment savedInvestment = investmentRepository.save(investment);
            logger.info("투자 데이터 저장 완료: investmentId = {}", savedInvestment.getInvestmentId());

            // 4. FundAchievement 데이터 생성 또는 업데이트
            logger.info("투자 목표 달성 데이터(FundAchievement) 업데이트 시작");
            updateFundAchievement(fund, company, dto.getInvestmentSumPrice());
            logger.info("투자 목표 달성 데이터 업데이트 완료");

            // 5. Account 엔티티 생성 및 저장
            logger.info("계좌 데이터 변환 및 저장 시작");
            Account account = toAccountEntity(dto, savedInvestment);
            accountRepository.save(account);
            logger.info("계좌 데이터 저장 완료: accountId = {}", account.getAccountId());

            // 6. 투자자산총괄데이터(InvestmentAssetSummary) 생성
            logger.info("투자자산총괄 데이터 생성 시작");
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
            logger.info("투자자산총괄 데이터 파생 필드 계산 시작");
            iasService.calculateDerivedFields(assetSummary, null);
            investmentAssetSummaryRepository.save(assetSummary);
            logger.info("투자자산총괄 데이터 저장 완료: assetSummaryId = {}", assetSummary.getInvestmentAssetSummaryId());

            // 7. 고용인력 데이터 생성
            logger.info("고용인력 데이터 생성 시작");
            Employment employment = Employment.builder()
                    .investment(savedInvestment)
                    .companyNm(company.getCompanyName())
                    .initialInvestmentDate(dto.getInvestmentDate())
                    .initialEmployeeCount(company.getEmployeeCount())
                    .latestEmployeeCount(null)
                    .finalRecoveryDate(null)
                    .finalEmployeeCount(null)
                    .build();
            employmentRepository.save(employment);
            logger.info("고용인력 데이터 저장 완료: employmentId = {}", employment.getId());

            return savedInvestment;

        } catch (IllegalArgumentException e) {
            logger.error("입력 데이터 오류 발생: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.error("데이터베이스 접근 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("Database error occurred", e);
        } catch (Exception e) {
            logger.error("알 수 없는 오류 발생: {}", e.getMessage());
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

    @Transactional
    public void updateFundAchievement(Fund fund, Company company, Long investmentAmount) {
        logger.info("[INFO] 투자 목표 달성 업데이트 시작 - 펀드 ID: {}", fund.getFundId());

        // 1️⃣ FundAchievement 데이터 조회 (없으면 null 반환)
        FundAchievement fundAchievement = fundAchievementRepository.findByFund(fund)
                .orElse(null);

        if (fundAchievement == null) {
            logger.warn("[WARN] FundAchievement 데이터 없음 - 펀드 ID: {}", fund.getFundId());
            return; // 🔥 데이터가 없으면 함수 종료 (추가 생성 X)
        }


// 2️⃣ 펀드의 투자 기준과 회사 속성을 비교하여 타겟어마운트

        if ("투자 잔액".equals(fundAchievement.getMandatoryCriteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getMandatoryTargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getMandatoryCriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setMandatoryTargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 의무 투자 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }


        if ("투자 잔액".equals(fundAchievement.getMainInvest1Criteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getMainInvest1TargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getMainInvest1CriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setMainInvest1TargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 주목적 투자 1 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }

        if ("투자 잔액".equals(fundAchievement.getMainInvest2Criteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getMainInvest2TargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getMainInvest2CriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setMainInvest2TargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 주목적 투자 2 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }

        if ("투자 잔액".equals(fundAchievement.getSpecialInvest1Criteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getSpecialInvest1TargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getSpecialInvest1CriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setSpecialInvest1TargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 특수목적 투자 1 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }

        if ("투자 잔액".equals(fundAchievement.getSpecialInvest2Criteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getSpecialInvest2TargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getSpecialInvest2CriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setSpecialInvest2TargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 특수목적 투자 2 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }

        if ("투자 잔액".equals(fundAchievement.getSpecialInvest3Criteria())) {
            BigDecimal targetAmount = BigDecimal.valueOf(fundAchievement.getSpecialInvest3TargetAmount());
            BigDecimal criteriaRatio = BigDecimal.valueOf(fundAchievement.getSpecialInvest3CriteriaRatio());
            BigDecimal investment = BigDecimal.valueOf(investmentAmount);

            // (기존 타겟 금액) + (투자 금액 * 기준 비율 / 100)
            BigDecimal updatedTargetAmount = targetAmount.add(
                    investment.multiply(criteriaRatio).divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
            );

            fundAchievement.setSpecialInvest3TargetAmount(updatedTargetAmount.longValue());

            logger.info("[UPDATE] 특수목적 투자 3 타겟 금액 갱신 - 기준: 투자 잔액 / 새로운 타겟 금액: {}", updatedTargetAmount);
        }



        //어마운트,비율 업데이트
        if (checkCompanyCriteria(company, fund.getMandatoryPurpose())) {
            Long newTotalAmount = fundAchievement.getMandatoryAmount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getMandatoryTargetAmount());

            fundAchievement.setMandatoryAmount(newTotalAmount);
            fundAchievement.setMandatoryRatio(newRatio);

            logger.info("[UPDATE] 의무 투자 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }

        if (checkCompanyCriteria(company, fund.getMainInvest1Purpose())) {
            Long newTotalAmount = fundAchievement.getMainInvest1Amount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getMainInvest1TargetAmount());

            fundAchievement.setMainInvest1Amount(newTotalAmount);
            fundAchievement.setMainInvest1Ratio(newRatio);

            logger.info("[UPDATE] 주목적 투자 1 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }

        if (checkCompanyCriteria(company, fund.getMainInvest2Purpose())) {
            Long newTotalAmount = fundAchievement.getMainInvest2Amount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getMainInvest2TargetAmount());

            fundAchievement.setMainInvest2Amount(newTotalAmount);
            fundAchievement.setMainInvest2Ratio(newRatio);

            logger.info("[UPDATE] 주목적 투자 2 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }

        if (checkCompanyCriteria(company, fund.getSpecialInvest1Purpose())) {
            Long newTotalAmount = fundAchievement.getSpecialInvest1Amount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getSpecialInvest1TargetAmount());

            fundAchievement.setSpecialInvest1Amount(newTotalAmount);
            fundAchievement.setSpecialInvest1Ratio(newRatio);

            logger.info("[UPDATE] 특수목적 투자 1 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }

        if (checkCompanyCriteria(company, fund.getSpecialInvest2Purpose())) {
            Long newTotalAmount = fundAchievement.getSpecialInvest2Amount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getSpecialInvest2TargetAmount());

            fundAchievement.setSpecialInvest2Amount(newTotalAmount);
            fundAchievement.setSpecialInvest2Ratio(newRatio);

            logger.info("[UPDATE] 특수목적 투자 2 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }

        if (checkCompanyCriteria(company, fund.getSpecialInvest3Purpose())) {
            Long newTotalAmount = fundAchievement.getSpecialInvest3Amount() + investmentAmount;
            Double newRatio = calculateRatio(newTotalAmount, fundAchievement.getSpecialInvest3TargetAmount());

            fundAchievement.setSpecialInvest3Amount(newTotalAmount);
            fundAchievement.setSpecialInvest3Ratio(newRatio);

            logger.info("[UPDATE] 특수목적 투자 3 업데이트 - 추가 금액: {} / 총 금액: {} / 비율: {}",
                    investmentAmount, newTotalAmount, newRatio);
        }



        // 3️⃣ 업데이트 후 저장
        fundAchievementRepository.save(fundAchievement);
        logger.info("[INFO] 투자 목표 달성 데이터 저장 완료 - 펀드 ID: {}", fund.getFundId());
    }


    private Double calculateRatio(Long amount, Long targetAmount) {
        // 입력값 로그 출력
        logger.info("[CALCULATE RATIO] amount: {}, targetAmount: {}", amount, targetAmount);

        if (targetAmount == null || targetAmount == 0) {
            return 0.0; // 분모가 0이면 0.0 반환
        }

        double ratio = (double) amount / targetAmount;
        BigDecimal roundedRatio = BigDecimal.valueOf(ratio * 100.0)
                .setScale(2, RoundingMode.DOWN); // 소수 셋째 자리부터 버림

        double finalRatio = roundedRatio.doubleValue(); // double로 변환

        // 계산된 비율 로그 출력
        logger.info("[CALCULATE RATIO] Calculated Ratio: {}", finalRatio);

        return finalRatio;
    }



    // 🔥 회사 속성이 펀드의 기준을 충족하는지 확인
    private boolean checkCompanyCriteria(Company company, String criteriaType) {
        if (criteriaType == null) return false;

        switch (criteriaType) {
            case "초기창업":
                return "창업 3년 이내".equals(company.getEarlyStartupType());
            case "상장하지 않은창업자":
                return "비상장기업".equals(company.getListingStatus());
            case "벤처기업":
                return "벤처기업".equals(company.getVentureRecognition());
            case "출자약정액 80%투자":
                return true;
            case "대학창업":
                return "Y".equals(company.getIsDaechang());
            case "학생창업":
                return "학생창업".equals(company.getStartupType());
            case "지방기업":
                return "지방창업".equals(company.getRegionalCompany());
            default:
                return false;
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
