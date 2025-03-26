package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.shrup.CShrupDTO;
import com.example.kuhidbs.dto.company.shrup.RShrupDTO;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.entity.company.ShareUpdate;
import com.example.kuhidbs.repository.CompanyAccountRepository;
import com.example.kuhidbs.repository.InvestmentAssetSummaryRepository;
import com.example.kuhidbs.repository.company.AccountRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import com.example.kuhidbs.repository.company.ShrupRepository;
import com.example.kuhidbs.service.Fund.IASService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShrupService {
    private static final Logger logger = LoggerFactory.getLogger(ShrupService.class);

    private final ShrupRepository shrupRepository;
    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final InvestmentAssetSummaryRepository investmentAssetSummaryRepository;
    private final IASService iasService;
    private final CompanyAccountRepository companyAccountRepository;


    /**
     * 감액/복원 데이터 저장
     */
    public ShareUpdate saveShrup(CShrupDTO shrupDTO) {
        try {
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            logger.info("==========================================");
            String updateType = shrupDTO.getShareUpdateType(); // 예: "감액" 또는 "환입"
            logger.info("Starting share {} process... [investmentId: {}]",
                    updateType.equals("감액") ? "reduction" : "restoration",
                    shrupDTO.getInvestmentId());
            // 1. 투자 고유 번호로 Investment 조회
            logger.info("Starting investment lookup: investmentId = {}", shrupDTO.getInvestmentId());
            Investment investment = investmentRepository.findById(shrupDTO.getInvestmentId())
                    .orElseThrow(() -> {
                        logger.error("Invalid investment ID: {}", shrupDTO.getInvestmentId());
                        return new IllegalArgumentException("Invalid investment ID: " + shrupDTO.getInvestmentId());
                    });
            logger.info("Investment found: investmentId = {}", investment.getInvestmentId());

            // 2. Company 객체 조회
            logger.info("Starting company lookup: companyId = {}", shrupDTO.getCompanyId());
            Company company = companyRepository.findById(shrupDTO.getCompanyId())
                    .orElseThrow(() -> {
                        logger.error("Invalid company ID: {}", shrupDTO.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + shrupDTO.getCompanyId());
                    });
            logger.info("Company found: companyId = {}", company.getCompanyId());

            // 3. DTO를 ShareUpdate 엔터티로 변환 후 저장
            logger.info("Converting and saving share update data...");
            ShareUpdate shareUpdate = ShareUpdate.builder()
                    .investment(investment)
                    .company(company)
                    .shareUpdateDate(shrupDTO.getShareUpdateDate())
                    .shareUnitValue(shrupDTO.getShareUnitValue())
                    .curUnitValue(shrupDTO.getCurUnitValue())
                    .shareUpdateType(shrupDTO.getShareUpdateType())
                    .shareUpdateReason(shrupDTO.getShareUpdateReason())
                    .shareUpdateAction(shrupDTO.getShareUpdateAction())
                    .build();
            ShareUpdate savedShareUpdate = shrupRepository.save(shareUpdate);
            logger.info("Share update saved: shareUpdateId = {}", savedShareUpdate.getShareUpdateId());

            // 4. 최신 계좌 데이터 조회
            logger.info("Looking up latest account data...");
            Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(shrupDTO.getInvestmentId());
            if (latestAccount == null) {
                logger.error("No account data found for investment ID: {}", shrupDTO.getInvestmentId());
                throw new IllegalArgumentException("No account data found for investment ID: " + shrupDTO.getInvestmentId());
            }
            logger.info("Latest account found: accountId = {}", latestAccount.getAccountId());


            // 5. 투자자산총괄 데이터 조회
            logger.info("Looking up investment asset summary...");
            InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());
            if (ias == null) {
                logger.error("No investment asset summary found for investment ID: {}", investment.getInvestmentId());
                throw new IllegalArgumentException("No Investment Asset Summary found for investment ID: " + investment.getInvestmentId());
            }
            logger.info("Investment asset summary found: iasId = {}", ias.getInvestmentAssetSummaryId());


            // 6. 감액 또는 환입 처리
            long additionalReduction;
            //감액시 리덕션 양수, 환입시 음수
            additionalReduction = latestAccount.getHeldShareCount() *
                    (latestAccount.getUnitPrice() - shrupDTO.getShareUnitValue());
            System.out.println("[INFO] additionalReduction: " + additionalReduction);

            // 7. 투자자산총괄표 갱신
            ias.setReductionAmount(ias.getReductionAmount() + additionalReduction);
            ias.setManagementFeeTarget(shrupDTO.getShareUnitValue() == 0 ? "부" : "여");
            ias.setRemainingAssetValuation(
                    //지금 가진주식 * 현재주당가치 = 잔여자산평가금액
                    shrupDTO.getShareUnitValue() == 0 ? 1000 : (shrupDTO.getCurUnitValue() * latestAccount.getHeldShareCount())
            );


            logger.info("Updating investment asset summary...");
            iasService.calculateDerivedFields(ias, null);
            logger.info("Investment asset summary updated.");

            // 8. 새로운 계좌 데이터 생성 및 저장
            logger.info("Updating account data...");
            Account updatedAccount = Account.builder()
                    .accountDate(shrupDTO.getShareUpdateDate())
                    .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                    .unitPrice(shrupDTO.getShareUnitValue()) // 새로운 주당 가치로 업데이트
                    .heldShareCount(latestAccount.getHeldShareCount()) // 기존 보유 주식 수 유지
                    .totalPrincipal(shrupDTO.getShareUnitValue() * latestAccount.getHeldShareCount()) // 투자 원금 업데이트
                    .functionType(shrupDTO.getShareUpdateType()) // 실행 함수 업데이트(감액 or 환입)
                    .kuhEquityRate(latestAccount.getKuhEquityRate()) // 기존 KUH 지분율 유지
                    .curUnitPrice(shrupDTO.getCurUnitValue()) // 현재 단가 업데이트
                    .totalShareCount(latestAccount.getTotalShareCount()) // 발행 총 주식 수 유지
                    .postValue(shrupDTO.getShareUnitValue() == 0
                            ? 1000
                            : shrupDTO.getShareUnitValue() * latestAccount.getTotalShareCount())
                    .kuhEquityRate(latestAccount.getKuhEquityRate())
                    .build();

            accountRepository.save(updatedAccount);
            logger.info("Account updated: accountId = {}", updatedAccount.getAccountId());

            return savedShareUpdate;


        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
            throw new RuntimeException("Database error occurred", e);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("Unexpected error occurred", e);
        }

    }

    /**
     * 특정 기업의 모든 감액/복원 데이터 조회
     */
    @Transactional(readOnly = true)
    public List<RShrupDTO> getAllShrupsByCompanyId(String companyId) {
        List<ShareUpdate> shareUpdates = shrupRepository.findByCompany_CompanyId(companyId);

        return shareUpdates.stream()
                .map(update -> new RShrupDTO(
                        update.getShareUpdateDate(),
                        update.getShareUnitValue(),
                        update.getCurUnitValue(),
                        update.getShareUpdateType(),
                        update.getShareUpdateReason(),
                        update.getShareUpdateAction(),
                        update.getCreatedAt(),
                        update.getUpdatedAt(),
                        update.getCreatedBy(),
                        update.getUpdatedBy()
                ))
                .collect(Collectors.toList());
    }

}
