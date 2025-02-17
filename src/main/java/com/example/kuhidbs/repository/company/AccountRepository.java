package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 투자 번호에 해당하는 가장 최신 계좌 데이터 조회 (최신 1건)
     */
    Account findTop1ByInvestmentInvestmentIdOrderByAccountIdDesc(Long investmentId);

    // 특정 투자 ID에 해당하는 모든 계좌 조회
    List<Account> findByInvestment_InvestmentIdOrderByAccountDateAsc(Long investmentId);
}
