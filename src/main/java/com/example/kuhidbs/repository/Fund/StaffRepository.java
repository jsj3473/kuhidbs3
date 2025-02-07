package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // 특정 조합(Fund) ID에 속한 운용인력 목록 조회
    List<Staff> findByFund_FundId(Long fundId);

    // 특정 운용인력 이름으로 조회 (변경 전 인력)
    List<Staff> findByPreviousStaff(String previousStaff);

    // 특정 운용인력 이름으로 조회 (변경 후 인력)
    List<Staff> findByCurrentStaff(String currentStaff);
}
