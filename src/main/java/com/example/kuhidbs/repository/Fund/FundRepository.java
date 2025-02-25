package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<Fund, String> {

    @Transactional
    @Modifying
    @Query("UPDATE Fund f SET f.currentStaff = :currentStaff WHERE f.fundId = :fundId")
    void updateCurrentStaffByFundId(@Param("fundId") String fundId, @Param("currentStaff") String currentStaff);

    // ✅ 모든 fundId와 fundName을 조회하는 메서드
    @Query("SELECT f.fundId, f.fundName FROM Fund f")
    List<Object[]> findAllFundIdAndName();

    @Query(value = "SELECT f.fundId, f.fundName FROM Fund f " +
            "WHERE f.fundId LIKE %:query% " +
            "OR f.fundName LIKE %:query% " +
            "OR CAST(f.fundId AS string) LIKE %:query%")
    List<Object[]> searchFunds(@Param("query") String query);
}
