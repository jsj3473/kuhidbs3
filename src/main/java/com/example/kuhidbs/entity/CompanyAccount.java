package com.example.kuhidbs.entity;

import com.example.kuhidbs.entity.company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "KUH_CMP_ACC_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAccount {

    @Id
    private String companyId; // FK이면서 PK 역할

    @OneToOne
    @MapsId // companyId를 Company의 PK와 동일하게 사용
    @JoinColumn(name = "company_id")
    private Company company;

    private Long totalSharesIssued; // 발행총주식수
    private BigDecimal pricePerShare; // 주당 단가
    private BigDecimal marketCap; // 시가총액
    private BigDecimal kuhOwnershipPercentage; // KUH 지분율
}
