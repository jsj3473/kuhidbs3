package com.example.kuhidbs.service.Fund;


import com.example.kuhidbs.dto.Fund.CFundMemDTO;
import com.example.kuhidbs.dto.Fund.RFundMemDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Fund.FundMem;
import com.example.kuhidbs.repository.Fund.FundMemRepository;
import com.example.kuhidbs.repository.Fund.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundMemService {

    private final FundMemRepository fundMemRepository;
    private final FundRepository fundRepository;



    @Transactional
    public List<FundMem> saveAll(List<CFundMemDTO> dtos) {
        if (dtos.isEmpty()) {
            return Collections.emptyList();
        }

        String fundId = dtos.get(0).getFundId(); // ✅ getFirst() → get(0)으로 변경

        // 기존 데이터 삭제
        fundMemRepository.deleteByFund_FundId(fundId);

        // 새로운 FundMem 리스트 생성
        List<FundMem> fundMems = dtos.stream().map(dto -> {
            Fund fund = fundRepository.findById(dto.getFundId())
                    .orElseThrow(() -> new RuntimeException("Fund ID " + dto.getFundId() + " 없음"));

            return FundMem.builder()
                    .fund(fund)
                    .memberType(dto.getMemberType())
                    .memberName(dto.getMemberName())
                    .committedUnitPrice(dto.getCommittedUnitPrice())
                    .contributionRate(dto.getContributionRate())
                    .businessType(dto.getBusinessType())
                    .businessRegNo(dto.getBusinessRegNo())
                    .contact(dto.getContact())
                    .residentRegNo(dto.getResidentRegNo())
                    .address(dto.getAddress())
                    .isActive(true)
                    .build();
        }).collect(Collectors.toList());

        // ✅ 특정 조건을 만족하는 데이터 처리 (후처리)
        fundMems.forEach(fundMem -> {
            if ("고려대학교기술지주주식회사".equals(fundMem.getMemberName())) {
                Fund fund = fundMem.getFund();
                fund.setIvtRatio(fundMem.getContributionRate()); // ✅ Fund의 ivtRatio 필드 업데이트
                fundRepository.save(fund); // ✅ 변경된 Fund 엔티티 저장 (JPA의 변경 감지를 위한 명시적 저장)
            }
        });

        // ✅ 중복된 return 제거 후 저장
        return fundMemRepository.saveAll(fundMems);
    }


    @Transactional(readOnly = true)
    public List<RFundMemDTO> getActiveFundMembersByFundId(String fundId) {
        return fundMemRepository.findActiveFundMembersByFundId(fundId);
    }
}
