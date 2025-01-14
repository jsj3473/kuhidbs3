package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.user.UpdatePasswordDTO;
import com.example.kuhidbs.dto.user.UserDTO;
import com.example.kuhidbs.entity.User;
import com.example.kuhidbs.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        // DB에 저장
        userRepository.save(user);
    }

    @Transactional
    public void retireUser(String id) {
        // 사용자 조회 및 재직 여부 업데이트
        User user = userRepository.findById(id).get(); // 사용자 조회
        user.setEmployed(false); // 재직 여부를 false로 설정
        userRepository.save(user); // 변경 사항 저장
    }

    @Transactional
    public void resetPassword(String id) {
        // 사용자 조회
        User user = userRepository.findById(id).get(); // 사용자 조회
        user.setPassword("1111"); // 비밀번호 초기화
        userRepository.save(user); // 변경 사항 저장
    }

    @Transactional
    public String updatePassword(String id, String currentPassword, String newPassword) {
        // 사번으로 사용자 조회
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return "사번과 비밀번호를 확인해주세요"; // 사번이 존재하지 않는 경우
        }

        // 현재 비밀번호 검증
        if (!user.getPassword().equals(currentPassword)) {
            return "사번과 비밀번호를 확인해주세요"; // 현재 비밀번호가 일치하지 않는 경우
        }

        // 비밀번호 변경
        user.setPassword(newPassword);
        userRepository.save(user); // 변경 사항 저장

        return "비밀번호가 변경되었습니다";
    }


    // isEmployed가 true인 모든 유저 가져오기
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findByIsEmployedTrue();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Entity -> DTO 변환 메서드
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getRegisteredBy(),
                user.getCreatedAt()
        );
    }
}
