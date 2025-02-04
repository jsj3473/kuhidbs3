package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Followup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * FollowupRepository
 *
 * 후속 투자(Followup) 엔터티와 데이터베이스 간의 상호작용을 처리하는 리포지토리.
 *
 * 작성일: 2025-01-28
 * 작성자: [작성자 이름]
 */
@Repository
public interface FollowupRepository extends JpaRepository<Followup, Long> {
    // 특정 회사의 가장 최신 투자 밸류 조회 (followupStartDate 기준 내림차순)
    Optional<Followup> findTopByCompany_CompanyIdOrderByFollowupStartDateDesc(String companyId);
}
