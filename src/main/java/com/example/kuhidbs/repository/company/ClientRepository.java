package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // 특정 회사 ID에 해당하는 피투자기업 실무자 데이터 조회
    Optional<Client> findByCompany_CompanyId(String companyId);
}

