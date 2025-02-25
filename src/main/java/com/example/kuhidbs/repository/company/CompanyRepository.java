package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query(value = "SELECT c.companyId, c.companyName FROM Company c " +
            "WHERE c.companyName LIKE %:query% " +
            "OR c.ceoName LIKE %:query% " +
            "OR CAST(c.companyId AS string) LIKE %:query%")
    List<Object[]> searchCompanies(@Param("query") String query);

    @Modifying
    @Transactional
    @Query(value = "UPDATE company SET {columnName} = :value WHERE company_id = :companyId", nativeQuery = true)
    void updateColumn(@Param("companyId") String companyId,
                      @Param("columnName") String columnName,
                      @Param("value") String value);
}