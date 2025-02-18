package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Audit;
import com.example.kuhidbs.entity.Fund.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    // 특정 조합(Fund) ID에 해당하는 감사 내역 조회
    //List<Audit> findByFund_FundId(String fundId);
    List<Audit> findByFund(Fund fund);
}