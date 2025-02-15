package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    // 특정 기업의 모든 무상증자 내역 조회
    List<Bonus> findByCompany_CompanyId(String companyId);
}
