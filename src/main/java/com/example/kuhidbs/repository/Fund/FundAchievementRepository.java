package com.example.kuhidbs.repository.Fund;

import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundAchievementRepository extends JpaRepository<FundAchievement, String> {

    // 특정 펀드의 FundAchievement 조회 (펀드 ID로 조회)
    Optional<FundAchievement> findByFund(Fund fund);

    // 특정 펀드 ID를 사용하여 조회
    Optional<FundAchievement> findByFund_FundId(String fundId);
}
