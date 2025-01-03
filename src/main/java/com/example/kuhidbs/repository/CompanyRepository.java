package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // 예: 회사명을 기준으로 검색
    //Company findByCompanyName(String companyName);
}
