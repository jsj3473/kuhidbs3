package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.history.RAccountDTO;
import com.example.kuhidbs.entity.company.Account;
import com.example.kuhidbs.repository.company.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * 특정 투자 ID에 해당하는 모든 계좌 정보 조회
     */
    @Transactional(readOnly = true)
    public List<RAccountDTO> getAllAccountsByInvestmentId(Long investmentId) {
        List<Account> accounts = accountRepository.findByInvestment_InvestmentIdOrderByAccountDateAsc(investmentId);

        return accounts.stream()
                .map(account -> new RAccountDTO(
                        account.getInvestment().getInvestmentId(),
                        account.getAccountDate(),
                        account.getUnitPrice(),
                        account.getHeldShareCount(),
                        account.getTotalPrincipal(),
                        account.getFunctionType(),
                        account.getKuhEquityRate(),
                        account.getCurUnitPrice(),
                        account.getPostValue(),
                        account.getTotalShareCount()
                ))
                .collect(Collectors.toList());
    }
}
