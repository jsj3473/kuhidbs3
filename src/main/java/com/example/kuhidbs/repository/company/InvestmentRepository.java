package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * IvtRepository
 *
 * 투자(Ivt) 엔터티와 데이터베이스 간의 상호작용을 처리하는 리포지토리.
 *
 * 작성일: 2025-01-28
 * 작성자: [작성자 이름]
 */
@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    // JpaRepository의 기본 메서드를 사용 (findAll, findById, save 등)


    Optional<Investment> findFirstByCompany_CompanyIdOrderByInvestmentIdDesc(String companyId);
}
