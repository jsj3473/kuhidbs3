package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.감액환입.CShrupDTO;
import com.example.kuhidbs.dto.company.감액환입.RShrupDTO;
import com.example.kuhidbs.entity.CompanyAccount;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShrupService {

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
            // 1. 투자 고유 번호로 Investment 조회
            System.out.println("[INFO] 투자 데이터 조회 시작: investmentId = " + shrupDTO.getInvestmentId());
            Investment investment = investmentRepository.findById(shrupDTO.getInvestmentId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] 유효하지 않은 투자 ID: " + shrupDTO.getInvestmentId());
                        return new IllegalArgumentException("Invalid investment ID: " + shrupDTO.getInvestmentId());
                    });
            System.out.println("[INFO] 투자 데이터 조회 성공: investmentId = " + investment.getInvestmentId());

            // 2. Company 객체 조회
            System.out.println("[INFO] 회사 데이터 조회 시작: companyId = " + shrupDTO.getCompanyId());
            Company company = companyRepository.findById(shrupDTO.getCompanyId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] 유효하지 않은 회사 ID: " + shrupDTO.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + shrupDTO.getCompanyId());
                    });
            System.out.println("[INFO] 회사 데이터 조회 성공: companyId = " + company.getCompanyId());

            // 3. DTO를 ShareUpdate 엔터티로 변환 후 저장
            System.out.println("[INFO] 감액/복원 데이터 변환 및 저장 시작");
            ShareUpdate shareUpdate = ShareUpdate.builder()
                    .investment(investment)
                    .company(company)
                    .shareUpdateDate(shrupDTO.getShareUpdateDate())
                    .shareUnitValue(shrupDTO.getShareUnitValue())
                    .shareUpdateType(shrupDTO.getShareUpdateType())
                    .shareUpdateReason(shrupDTO.getShareUpdateReason())
                    .shareUpdateAction(shrupDTO.getShareUpdateAction())
                    .build();
            ShareUpdate savedShareUpdate = shrupRepository.save(shareUpdate);
            System.out.println("[INFO] 감액/복원 데이터 저장 완료: shareUpdateId = " + savedShareUpdate.getShareUpdateId());

            // 4. 최신 계좌 데이터 조회
            System.out.println("[INFO] 최신 계좌 데이터 조회 시작");
            Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(shrupDTO.getInvestmentId());
            if (latestAccount == null) {
                System.out.println("[ERROR] 해당 투자 ID에 대한 계좌 데이터가 존재하지 않음: " + shrupDTO.getInvestmentId());
                throw new IllegalArgumentException("No account data found for investment ID: " + shrupDTO.getInvestmentId());
            }
            System.out.println("[INFO] 최신 계좌 데이터 조회 성공: accountId = " + latestAccount.getAccountId());

            // 5. 투자자산총괄 데이터 조회
            System.out.println("[INFO] 투자자산총괄 데이터 조회 시작");
            InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());
            if (ias == null) {
                System.out.println("[ERROR] 투자자산총괄 데이터가 존재하지 않음: investmentId = " + investment.getInvestmentId());
                throw new IllegalArgumentException("No Investment Asset Summary found for investment ID: " + investment.getInvestmentId());
            }
            System.out.println("[INFO] 투자자산총괄 데이터 조회 성공: iasId = " + ias.getInvestmentAssetSummaryId());

            // 6. 감액 또는 환입 처리
            long additionalReduction;
            if ("감액".equals(shrupDTO.getShareUpdateType())) {
                System.out.println("[INFO] 감액 처리 시작");
                additionalReduction = latestAccount.getHeldShareCount() *
                        (latestAccount.getUnitPrice() - shrupDTO.getShareUnitValue());
            } else {
                System.out.println("[INFO] 환입 처리 시작");
                additionalReduction = latestAccount.getHeldShareCount() *
                        (latestAccount.getUnitPrice() - shrupDTO.getShareUnitValue());
            }
            System.out.println("[INFO] 추가 감액 금액: " + additionalReduction);

            // 7. 투자자산총괄표 갱신
            ias.setReductionAmount(ias.getReductionAmount() + additionalReduction);
            ias.setManagementFeeTarget(shrupDTO.getShareUnitValue() == 0 ? "부" : "여");
            ias.setRemainingAssetValuation(
                    shrupDTO.getShareUnitValue() == 0 ? 1000 : (shrupDTO.getShareUnitValue() * latestAccount.getHeldShareCount())
            );


            System.out.println("[INFO] 투자자산총괄 데이터 갱신 시작");
            iasService.calculateDerivedFields(ias, null);
            System.out.println("[INFO] 투자자산총괄 데이터 갱신 완료");

            // 8. 새로운 계좌 데이터 생성 및 저장
            System.out.println("[INFO] 계좌 데이터 갱신 시작");
            Account updatedAccount = Account.builder()
                    .accountDate(shrupDTO.getShareUpdateDate())
                    .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                    .unitPrice(shrupDTO.getShareUnitValue()) // 새로운 주당 가치로 업데이트
                    .heldShareCount(latestAccount.getHeldShareCount()) // 기존 보유 주식 수 유지
                    .totalPrincipal(shrupDTO.getShareUnitValue() * latestAccount.getHeldShareCount()) // 투자 원금 업데이트
                    .functionType(shrupDTO.getShareUpdateType()) // 실행 함수 업데이트(감액 or 환입)
                    .kuhEquityRate(latestAccount.getKuhEquityRate()) // 기존 KUH 지분율 유지
                    .curUnitPrice(shrupDTO.getShareUnitValue()) // 현재 단가 업데이트
                    .totalShareCount(latestAccount.getTotalShareCount()) // 발행 총 주식 수 유지
                    .postValue(shrupDTO.getShareUnitValue() == 0
                            ? 1000
                            : shrupDTO.getShareUnitValue() * latestAccount.getTotalShareCount())
                    .kuhEquityRate(latestAccount.getKuhEquityRate())
                    .build();

            accountRepository.save(updatedAccount);
            System.out.println("[INFO] 계좌 데이터 갱신 완료: accountId = " + updatedAccount.getAccountId());

            return savedShareUpdate;

        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 입력 데이터 오류 발생: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            System.out.println("[ERROR] 데이터베이스 접근 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("Database error occurred", e);
        } catch (Exception e) {
            System.out.println("[ERROR] 알 수 없는 오류 발생: " + e.getMessage());
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
                        update.getShareUpdateType(),
                        update.getShareUpdateReason(),
                        update.getShareUpdateAction()
                ))
                .collect(Collectors.toList());
    }

}
