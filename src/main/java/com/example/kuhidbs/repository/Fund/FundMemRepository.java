package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.dto.Fund.RFundMemDTO;
import com.example.kuhidbs.entity.Fund.FundMem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundMemRepository extends JpaRepository<FundMem, Long> {



    @Query("SELECT new com.example.kuhidbs.dto.Fund.RFundMemDTO(fm.fund.fundId, fm.memberName, fm.memberType, " +
            "fm.contact, fm.regNo, fm.address, fm.committedUnitPrice, fm.contributionRate) " +
            "FROM FundMem fm " +
            "WHERE fm.fund.fundId = :fundId")
    List<RFundMemDTO> findFundMembersByFundId(@Param("fundId") String fundId);


    void deleteByFund_FundId(String fundFundId);
}
