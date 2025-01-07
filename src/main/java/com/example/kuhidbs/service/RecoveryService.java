package com.example.kuhidbs.service;

import com.example.kuhidbs.entity.Recovery;
import com.example.kuhidbs.repository.RecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryService {

    private final RecoveryRepository recoveryRepository;

    @Autowired
    public RecoveryService(RecoveryRepository recoveryRepository) {
        this.recoveryRepository = recoveryRepository;
    }

    /**
     * 회수 정보 생성
     *
     * @param recovery 저장할 회수 정보 엔터티
     */
    public void createRecovery(Recovery recovery) {
        recoveryRepository.save(recovery);
    }
}
