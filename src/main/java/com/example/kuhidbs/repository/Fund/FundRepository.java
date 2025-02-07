package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
}
