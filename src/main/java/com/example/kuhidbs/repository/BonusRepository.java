package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
