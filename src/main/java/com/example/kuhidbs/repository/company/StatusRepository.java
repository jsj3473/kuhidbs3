package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findTopByCompany_CompanyIdOrderByStatusIdDesc(String companyId);
}