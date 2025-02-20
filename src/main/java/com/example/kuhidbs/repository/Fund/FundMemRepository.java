package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.FundMem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundMemRepository extends JpaRepository<FundMem, Long> {
}
