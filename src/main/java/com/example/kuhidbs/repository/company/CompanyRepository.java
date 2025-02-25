package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query(value = "SELECT c.companyId, c.companyName FROM Company c " +
            "WHERE c.companyName LIKE %:query% " +
            "OR c.ceoName LIKE %:query% " +
            "OR CAST(c.companyId AS string) LIKE %:query%")
    List<Object[]> searchCompanies(@Param("query") String query);
}