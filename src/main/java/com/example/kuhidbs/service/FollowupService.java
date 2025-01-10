package com.example.kuhidbs.service;

import com.example.kuhidbs.entity.Followup;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.FollowupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowupService {

    private final FollowupRepository followupRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public FollowupService(FollowupRepository followupRepository, CompanyRepository companyRepository) {
        this.followupRepository = followupRepository;
        this.companyRepository = companyRepository;
    }

    public void createFollowup(Followup followup) {
        //여기에 현재기업가치 최신화코드작성하기 아직 어떻게 구현할지 못정함
        followupRepository.save(followup);
    }
}
