package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "KUH_MNGCHNG_HST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reviewer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "CHNG_ID", nullable = false)
    private Long changeId; // String → Long 변경

    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "CHNG_CMT", length = 1000)
    private String comment;

    @Column(name = "MNG_TYPE", length = 100)
    private String managerType;

    @Column(name = "MNG_EMP", length = 100)
    private String manager;

    @Column(name = "CHNG_RESN", length = 1000)
    private String changeReason;
}
