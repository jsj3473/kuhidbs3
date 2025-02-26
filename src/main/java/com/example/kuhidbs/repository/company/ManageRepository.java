package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ManageRepository extends JpaRepository<Manage, Long> {
    Optional<Manage> findTopByCompany_CompanyIdOrderByManageIdDesc(String companyId);

    List<Manage> findAllByCompany_CompanyId(String companyId);
}

