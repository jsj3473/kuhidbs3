package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.dto.company.동반.RComDTO;
import com.example.kuhidbs.entity.company.Combine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombineRepository extends JpaRepository<Combine, Long> {
    List<Combine> findByCompany_CompanyId(String companyId);
}
