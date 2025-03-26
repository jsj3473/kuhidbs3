package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.staff.CStaffDTO;
import com.example.kuhidbs.dto.Fund.staff.RStaffDTO;
import com.example.kuhidbs.entity.Fund.*;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.Fund.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final FundRepository fundRepository;

    // 운용인력 정보 생성
    public Staff createStaff(CStaffDTO dto) {
        Fund fund = fundRepository.findById(dto.getFundId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 조합 ID: " + dto.getFundId()));

        Staff staff = Staff.builder()
                .fund(fund)
                .changeDate(dto.getChangeDate())
                .previousStaff(dto.getPreviousStaff())
                .currentStaff(dto.getCurrentStaff())
                .resignDate(dto.getResignDate())
                .reasonAndSanction(dto.getReasonAndSanction())
                .build();

        return staffRepository.save(staff);
    }

    // 특정 fundId에 해당하는 모든 Staff 변경 내역 조회
    public List<RStaffDTO> getStaffChangesByFundId(String fundId) {
        List<Staff> staffChanges = staffRepository.findByFund_FundId(fundId);

        return staffChanges.stream().map(staff -> RStaffDTO.builder()
                .changeDate(staff.getChangeDate())
                .previousStaff(staff.getPreviousStaff())
                .currentStaff(staff.getCurrentStaff())
                .resignDate(staff.getResignDate())
                .reasonAndSanction(staff.getReasonAndSanction())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .createdBy(staff.getCreatedBy())
                .updatedBy(staff.getUpdatedBy())
                .build()
        ).collect(Collectors.toList());
    }
}