package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.InvestmentAssetSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentAssetSummaryRepository extends JpaRepository<InvestmentAssetSummary, Long> {
    // 투자 엔터티의 id를 기준으로 InvestmentAssetSummary 조회
    InvestmentAssetSummary findByInvestment_InvestmentId(Long investmentId);
    List<InvestmentAssetSummary> findByFund_FundId(String fundId);
    @Query("SELECT COALESCE(SUM(i.recoveredPrincipal), 0) FROM InvestmentAssetSummary i WHERE i.fund.fundId = :fundId")
    Integer sumRecoveredPrincipalByFundId(String fundId);

    @Query("SELECT COALESCE(SUM(i.recoveredProfit), 0) FROM InvestmentAssetSummary i WHERE i.fund.fundId = :fundId")
    Integer sumRecoveredProfitByFundId(String fundId);

    @Query("SELECT COALESCE(SUM(i.investmentAmount), 0) FROM InvestmentAssetSummary i WHERE i.fund = :fund")
    Optional<Long> sumInvestmentAmountByFund(@Param("fund") Fund fund);
}
