package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Long> {

    // 추가적인 쿼리 메서드가 필요하면 여기서 정의할 수 있습니다.
}
