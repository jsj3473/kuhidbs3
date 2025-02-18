package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundFinancial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundFinancialRepository extends JpaRepository<FundFinancial, Long> {
    List<FundFinancial> findByFund_FundId(String fundId);
}
