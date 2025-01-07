package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Integer> {
}
