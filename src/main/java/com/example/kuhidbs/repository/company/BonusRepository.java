package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
