package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.kuhINV.CIvtDTO;
import com.example.kuhidbs.dto.company.kuhINV.RIvtDTO;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
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

            // 4. FundAchievement 데이터 생성 또는 업데이트
            logger.info("투자 목표 달성 데이터(FundAchievement) 업데이트 시작");
            updateFundAchievement(fund, company, dto.getInvestmentSumPrice(), assetSummary);
            logger.info("투자 목표 달성 데이터 업데이트 완료");

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
    public void updateFundAchievement(Fund fund, Company company, Long investmentAmount, InvestmentAssetSummary ias) {
        logger.info("[INFO] 투자 목표 달성 업데이트 시작 - 펀드 ID: {}", fund.getFundId());

        FundAchievement fundAchievement = fundAchievementRepository.findByFund(fund).orElse(null);
        if (fundAchievement == null) {
            logger.warn("[WARN] FundAchievement 데이터 없음 - 펀드 ID: {}", fund.getFundId());
            return;
        }

        List<String> criteriaList = Arrays.asList(
                fundAchievement.getMandatoryCriteria(),
                fundAchievement.getMainInvest1Criteria(),
                fundAchievement.getMainInvest2Criteria(),
                fundAchievement.getSpecialInvest1Criteria(),
                fundAchievement.getSpecialInvest2Criteria(),
                fundAchievement.getSpecialInvest3Criteria()
        );

        List<Long> targetAmounts = Arrays.asList(
                fundAchievement.getMandatoryTargetAmount(),
                fundAchievement.getMainInvest1TargetAmount(),
                fundAchievement.getMainInvest2TargetAmount(),
                fundAchievement.getSpecialInvest1TargetAmount(),
                fundAchievement.getSpecialInvest2TargetAmount(),
                fundAchievement.getSpecialInvest3TargetAmount()
        );


        List<Consumer<Long>> setTargetAmountFunctions = Arrays.asList(
                fundAchievement::setMandatoryTargetAmount,
                fundAchievement::setMainInvest1TargetAmount,
                fundAchievement::setMainInvest2TargetAmount,
                fundAchievement::setSpecialInvest1TargetAmount,
                fundAchievement::setSpecialInvest2TargetAmount,
                fundAchievement::setSpecialInvest3TargetAmount
        );

        List<Supplier<Long>> getAmountFunctions = Arrays.asList(
                fundAchievement::getMandatoryAmount,
                fundAchievement::getMainInvest1Amount,
                fundAchievement::getMainInvest2Amount,
                fundAchievement::getSpecialInvest1Amount,
                fundAchievement::getSpecialInvest2Amount,
                fundAchievement::getSpecialInvest3Amount
        );

        List<Consumer<Long>> setAmountFunctions = Arrays.asList(
                fundAchievement::setMandatoryAmount,
                fundAchievement::setMainInvest1Amount,
                fundAchievement::setMainInvest2Amount,
                fundAchievement::setSpecialInvest1Amount,
                fundAchievement::setSpecialInvest2Amount,
                fundAchievement::setSpecialInvest3Amount
        );

        List<Consumer<Double>> setRatioFunctions = Arrays.asList(
                fundAchievement::setMandatoryRatio,
                fundAchievement::setMainInvest1Ratio,
                fundAchievement::setMainInvest2Ratio,
                fundAchievement::setSpecialInvest1Ratio,
                fundAchievement::setSpecialInvest2Ratio,
                fundAchievement::setSpecialInvest3Ratio
        );

        List<String> purposeList = Arrays.asList(
                fund.getMandatoryPurpose(),
                fund.getMainInvest1Purpose(),
                fund.getMainInvest2Purpose(),
                fund.getSpecialInvest1Purpose(),
                fund.getSpecialInvest2Purpose(),
                fund.getSpecialInvest3Purpose()
        );

        for (int i = 0; i < criteriaList.size(); i++) {
            // 기준이 투자잔액일 경우 투자금이 출자약정액의 80% 이상이면 투자잔액에 투자원금을 삽입
            // 투자 금액 합산 값을 가져옴
            Long totalInvestmentAmount = investmentAssetSummaryRepository
                    .sumInvestmentAmountByFund(fund)
                    .orElse(0L); // 값이 없으면 기본값 0 사용

            // 비교 및 저장 로직
            if ("투자 잔액".equals(criteriaList.get(i)) && totalInvestmentAmount > fund.getCommittedTotalPrice() * 0.8) {
                setTargetAmountFunctions.get(i).accept(totalInvestmentAmount);

                // 🔹 targetAmounts 리스트 최신화
                targetAmounts.set(i, totalInvestmentAmount);

                // fundAchievement 값이 올바르게 설정되었는지 확인 후 저장
                fundAchievementRepository.save(fundAchievement);
                logger.info("[SAVE] 타겟어마운트 변경됨 fundAchievement 저장 완료");
            }


            // 회사가 펀드의 조건을 만족하는 경우
            if (purposeList.get(i) != null && checkCompanyCriteria(company, purposeList.get(i))) {
                Long currentAmount = getAmountFunctions.get(i).get();
                Long investmentAmountSafe = (investmentAmount != null) ? investmentAmount : 0L;
                Long newTotalAmount = currentAmount + investmentAmountSafe;

                setAmountFunctions.get(i).accept(newTotalAmount);

                // targetAmounts.get(i)가 0이 아닐 경우만 비율 계산
                Double newRatio = (targetAmounts.get(i) != 0) ? calculateRatio(newTotalAmount, targetAmounts.get(i)) : 0.0;
                setRatioFunctions.get(i).accept(newRatio);

                logger.info("[UPDATE] 투자 업데이트 - 인덱스: {} / 추가 금액: {} / 총 금액: {} / 비율: {}",
                        i, investmentAmountSafe, newTotalAmount, newRatio);
            } else {
                // 회사가 조건을 만족하지 않는 경우
                Long currentAmount = getAmountFunctions.get(i).get();
                Double newRatio = (targetAmounts.get(i) != 0) ? calculateRatio(currentAmount, targetAmounts.get(i)) : 0.0;
                setRatioFunctions.get(i).accept(newRatio);

                logger.info("[UPDATE] 투자 업데이트 - 인덱스: {} / 변경 없음 / 총 금액: {} / 비율: {}",
                        i, currentAmount, newRatio);
            }
        }


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
                return "대창창업기업".equals(company.getIsDaechang());
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
