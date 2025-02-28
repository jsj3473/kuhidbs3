package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "INVESTMENT_STATUS_TBL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Status extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가 설정
    @Column(name = "INVESTMENT_STATUS_ID", nullable = false)
    private Long statusId; // 투자 상태 고유번호 (PK, Auto Increment)

    @Column(name = "INVESTMENT_STATUS", length = 255, nullable = false)
    private String status; // 투자 상태 (예: 진행 중, 완료 등)

    @Column(name = "ADDITIONAL_INFO", columnDefinition = "TEXT")
    private String additionalInfo; // 기타 정보

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company; // 회사 (FK)
}
