package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.company.Investment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "investment_id", nullable = false)
    private Investment investment; // 투자 기업

    @Column(name = "companyNm")
    private String companyNm; // 투자기업

    @Column(name = "initial_investment_date")
    private String initialInvestmentDate; // 최초 투자일자

    @Column(name = "initial_employee_count")
    private Integer initialEmployeeCount; // 최초 투자 시점 인력 수

    @Column(name = "latest_employee_count")
    private Integer latestEmployeeCount; // 최신 인력 수

    @Column(name = "final_recovery_date")
    private String finalRecoveryDate; // 최종 회수일자

    @Column(name = "final_employee_count")
    private Integer finalEmployeeCount; // 최종 회수 시점 인력 수

    @Column(name = "FNC_YEAR")
    private Integer financialYear; //기준년도

    @Column(name = "FNC_HALF")
    private String financialHalf; //기준반기
}
