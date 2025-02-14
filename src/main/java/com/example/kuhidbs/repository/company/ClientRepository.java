package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // 가장 최근 데이터를 하나만 반환 (createdAt 기준)
    Optional<Client> findTopByCompany_CompanyIdOrderByCreatedAtDesc(String companyId);

    // 만약 createdAt이 없다면 id 기준으로 최신 데이터 조회
    Optional<Client> findTopByCompany_CompanyIdOrderByClientIdDesc(String companyId);

}


