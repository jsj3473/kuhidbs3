package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
