package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FundRepository extends JpaRepository<Fund, String> {

    @Transactional
    @Modifying
    @Query("UPDATE Fund f SET f.currentStaff = :currentStaff WHERE f.fundId = :fundId")
    void updateCurrentStaffByFundId(@Param("fundId") String fundId, @Param("currentStaff") String currentStaff);
}
