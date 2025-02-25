package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.company.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

    @Query("SELECT e FROM Employment e WHERE e.investment.fund.fundId = :fundId")
    List<Employment> findByFundId(@Param("fundId") String fundId);

    Employment findByInvestment(Investment investment);
}
