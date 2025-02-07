package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {

    // 최신 발굴자 가져오기
    Optional<Reviewer> findTopByCompany_CompanyIdAndManagerTypeOrderByChangeIdDesc(String companyId, String managerType);
}
