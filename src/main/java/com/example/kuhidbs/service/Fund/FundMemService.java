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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundMemService {

    private final FundMemRepository fundMemRepository;
    private final FundRepository fundRepository;

    /**
     * List<CFundMemDTO>를 받아 FundMem 엔터티로 변환 후 저장
     */
    @Transactional
    public List<FundMem> saveAll(List<CFundMemDTO> dtos) {
        List<FundMem> fundMems = dtos.stream().map(dto -> {
            // Fund 엔터티 조회
            Fund fund = fundRepository.findById(dto.getFundId())
                    .orElseThrow(() -> new RuntimeException("Fund ID " + dto.getFundId() + " 없음"));

            // FundMem 객체 직접 생성
            return FundMem.builder()
                    .fund(fund)  // Fund 엔터티 참조
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

        return fundMemRepository.saveAll(fundMems); // 리스트 저장
    }

    public List<RFundMemDTO> getActiveFundMembersByFundId(String fundId) {
        return fundMemRepository.findActiveFundMembersByFundId(fundId);
    }
}
