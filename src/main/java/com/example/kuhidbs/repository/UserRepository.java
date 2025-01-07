package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 필요한 추가 메서드가 있다면 여기에 정의
}
