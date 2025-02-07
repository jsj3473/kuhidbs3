package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "KUH_CLIENT_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "CLIENT_ID", nullable = false)
    private Long clientId; // String → Long 변경


    @ManyToOne
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "CLIENT_POSI_TP", length = 100)
    private String positionType;

    @Column(name = "CLIENT_PHONNUM", length = 100)
    private String phoneNumber;

    @Column(name = "CLIENT_EML", length = 100)
    private String email;

    @Column(name = "CLIENT_NM", length = 100)
    private String name;
}