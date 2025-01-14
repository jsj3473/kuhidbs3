package com.example.kuhidbs.repository;

import com.example.kuhidbs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // isEmployed가 true인 사용자만 가져오기
    List<User> findByIsEmployedTrue();
}
