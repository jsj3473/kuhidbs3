package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Combine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombineRepository extends JpaRepository<Combine, Long> {
}
