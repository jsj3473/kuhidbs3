package com.example.kuhidbs.entity.Fund;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.company.Investment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "kuh_investment_due_diligence_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DueDiligence extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "investment_id", nullable = false)
    private Investment investment;

    @ManyToOne
    @JoinColumn(name = "fund_id", nullable = false)
    private Fund fund;

    @Column(name = "target", length = 2550) //실사대상
    private String target;

    @Column(name = "status", length = 50) //실시여부
    private String status;

    @Column(name = "inspection_date", nullable = true, length = 50) //실사일자
    private String inspectionDate;

    @Column(name = "issues", nullable = true, length = 2550)  //문제점발견
    private String issues;
}
