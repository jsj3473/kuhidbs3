package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, Integer> {
}
