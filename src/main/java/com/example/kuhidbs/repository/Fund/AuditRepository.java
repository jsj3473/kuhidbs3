package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {

    // 특정 조합(Fund) ID에 해당하는 감사 내역 조회
    List<Audit> findByFund_FundId(Long fund_fundId);
}