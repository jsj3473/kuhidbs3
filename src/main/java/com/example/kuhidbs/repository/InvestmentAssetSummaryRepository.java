package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.InvestmentAssetSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentAssetSummaryRepository extends JpaRepository<InvestmentAssetSummary, Long> {
    // 투자 엔터티의 id를 기준으로 InvestmentAssetSummary 조회
    InvestmentAssetSummary findByInvestment_InvestmentId(Long investmentId);
    List<InvestmentAssetSummary> findByFund_FundId(String fundId);
}
