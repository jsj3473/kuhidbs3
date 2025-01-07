package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Followup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowupRepository extends JpaRepository<Followup, Integer> {

    /**
     * 특정 회사의 모든 후속 투자 조회
     *
     * @param companyId 회사 ID
     * @return 후속 투자 리스트
     */
    List<Followup> findByCompanyCompanyId(Integer companyId);
}
