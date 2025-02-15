package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.ShareUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShrupRepository
 *
 * 감액/복원(ShareUpdate) 엔터티와 데이터베이스 간의 상호작용을 처리하는 레포지토리.
 *
 * 작성일: 2025-01-28
 * 작성자: [작성자 이름]
 */
@Repository
public interface ShrupRepository extends JpaRepository<ShareUpdate, Long> {
    // JpaRepository의 기본 메서드 사용 (findAll, findById, save 등)
    // 특정 기업의 모든 감액/복원 데이터 조회
    List<ShareUpdate> findByCompany_CompanyId(String companyId);
}
