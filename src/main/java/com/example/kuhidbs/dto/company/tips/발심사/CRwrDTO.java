package com.example.kuhidbs.dto.company.tips.발심사;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CRwrDTO {

    private String companyId; // Company 객체 대신 ID만 포함
    private String managerType;
    private String manager;
    private String changeReason;
}
