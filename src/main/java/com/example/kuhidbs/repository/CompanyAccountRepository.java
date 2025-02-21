package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, String> {
    Optional<CompanyAccount> findByCompanyId(String companyId);
}
