package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.DueDiligence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DueDiligenceRepository extends JpaRepository<DueDiligence, Long>{
    List<DueDiligence> findByFund_FundId(String fundId);
}
