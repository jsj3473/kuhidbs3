package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    // 예: 회사명을 기준으로 검색
    //Company findByCompanyName(String companyName);
}
