package com.example.kuhidbs.repository.company;

import java.util.List;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.StockSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSplitRepository extends JpaRepository<StockSplit, Long> {
    List<StockSplit> findByCompany(Company company);
}
