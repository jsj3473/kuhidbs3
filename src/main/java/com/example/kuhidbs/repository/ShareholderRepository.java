package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Shareholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareholderRepository extends JpaRepository<Shareholder, Long> {
    Optional<Shareholder> findByCompany_CompanyId(String companyId);
}
