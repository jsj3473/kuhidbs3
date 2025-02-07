package com.example.kuhidbs.service.Fund;

import com.example.kuhidbs.dto.Fund.CStaffDTO;
import com.example.kuhidbs.entity.Fund.Fund;
import com.example.kuhidbs.entity.Staff;
import com.example.kuhidbs.repository.Fund.FundRepository;
import com.example.kuhidbs.repository.Fund.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}