package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    List<Reviewer> findByCompany_companyId(int companyId);
}
