package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FinancialRepository extends JpaRepository<Financial, Long> {

    // 특정 회사의 최근 2개 재무제표 조회 (연도 내림차순, 반기 내림차순 정렬)
    List<Financial> findTop2ByCompany_CompanyIdOrderByFinancialYearDescFinancialHalfDesc(String companyId);
}
