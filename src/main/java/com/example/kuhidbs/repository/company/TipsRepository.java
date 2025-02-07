package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.TIPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipsRepository extends JpaRepository<TIPS, Long> {

    // 특정 회사의 TIPS 현황 조회
    List<TIPS> findByCompany_CompanyId(String companyId);
}
