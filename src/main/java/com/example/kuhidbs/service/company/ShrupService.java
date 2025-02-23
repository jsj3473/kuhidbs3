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
        // 투자 고유 번호로 Ivt 객체 조회
        Investment investment = investmentRepository.findById(shrupDTO.getInvestmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + shrupDTO.getInvestmentId()));

        Company company = companyRepository.findById(shrupDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + shrupDTO.getCompanyId()));

        // DTO를 엔터티로 변환
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

        // 최신 계좌 데이터 조회
        Account latestAccount = accountRepository.findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(shrupDTO.getInvestmentId());
        InvestmentAssetSummary ias = investmentAssetSummaryRepository.findByInvestment_InvestmentId(investment.getInvestmentId());

        //투자자산총괄표갱신
        long additionalReduction;
        if(Objects.equals(shrupDTO.getShareUpdateType(), "감액"))
        {
            //System.out.println("감액");
            // reductionAmount 값 업데이트:
            // 기존 reductionAmount + (보유 주식수 * (기존 단가 - 새로운 단가))
            additionalReduction = latestAccount.getHeldShareCount() *
                    (latestAccount.getUnitPrice() - shrupDTO.getShareUnitValue());
            //System.out.println(additionalReduction);

        }
        else{
            //System.out.println("환입");
            additionalReduction = latestAccount.getHeldShareCount() *
                    (latestAccount.getUnitPrice() - shrupDTO.getShareUnitValue());
            //System.out.println(additionalReduction);
        }

        ias.setReductionAmount(ias.getReductionAmount() + additionalReduction);
        iasService.calculateDerivedFields(ias);
        investmentAssetSummaryRepository.save(ias);

        // 일부 컬럼 수정
        Account updatedAccount = Account.builder()
                .accountDate(shrupDTO.getShareUpdateDate())
                .investment(latestAccount.getInvestment()) // 기존 투자 엔터티 유지
                .unitPrice(shrupDTO.getShareUnitValue()) // 새로운 주당 가치로 업데이트
                .heldShareCount(latestAccount.getHeldShareCount()) // 기존 보유 주식 수 유지
                .totalPrincipal(shrupDTO.getShareUnitValue()*latestAccount.getHeldShareCount()) // 투자 원금 업데이트
                .functionType(shrupDTO.getShareUpdateType()) // 실행 함수 업데이트(감액 or 환입)
                .kuhEquityRate(latestAccount.getKuhEquityRate()) // 기존 KUH 지분율 유지
                .curUnitPrice(shrupDTO.getShareUnitValue()) //현재단가 업데이트
                .totalShareCount(latestAccount.getTotalShareCount()) //발행총주식수는 변함없음
                .postValue(
                        shrupDTO.getShareUnitValue() == 0
                                ? 1000
                                : shrupDTO.getShareUnitValue() * latestAccount.getTotalShareCount()
                )
                .kuhEquityRate(latestAccount.getKuhEquityRate())
                .build();

        accountRepository.save(updatedAccount);
        return savedShareUpdate;
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
