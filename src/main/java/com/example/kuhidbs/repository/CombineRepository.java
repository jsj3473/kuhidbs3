package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Combine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombineRepository extends JpaRepository<Combine, Long> {
}
