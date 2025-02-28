package com.example.kuhidbs.entity.company;

import com.example.kuhidbs.entity.BaseEntity;
import com.example.kuhidbs.entity.CompanyAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KUH_CMP_INFO")
@Data // 자동으로 Getter, Setter, toString, equals, hashCode 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Builder // 빌더 패턴 지원
public class Company extends BaseEntity {

    @Id
    @Column(name = "CMP_ID", length = 80, nullable = false)
    private String companyId;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Investment> investments;


    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private CompanyAccount companyAccount;

    @Column(name = "CMP_NM", length = 100, nullable = false)
    private String companyName;

    @Column(name = "EST_DATE", length = 100)
    private String establishmentDate;

    @Column(name = "CEO_NM", length = 100)
    private String ceoName;

    @Column(name = "CMP_ADR", length = 100)
    private String companyAddress;

    @Column(name = "BIZ_REG_NO", length = 100)
    private String businessRegistrationNumber;

    @Column(name = "CORP_REG_NO", length = 100)
    private String corporateRegistrationNumber;

    @Column(name = "IDST_CD", length = 100)
    private String industryCode;

    @Column(name = "CAPITAL")
    private Integer capital;

    @Column(name = "PAR_VAL")
    private Integer parValue;

    @Column(name = "EMP_CNT")
    private Integer employeeCount;

    @Column(name = "STRTUP_TP", length = 100)
    private String startupType;

    @Column(name = "RGN_YN", length = 100)
    private String regionalCompany;

    @Column(name = "KUH_YN", length = 100)
    private String kuhStartup;

    @Column(name = "VENT_YN", length = 100)
    private String ventureRecognition;

    @Column(name = "RSRCH_YN", length = 100)
    private String researchRecognition;

    @Column(name = "EARLY_STRT_TP", length = 100)
    private String earlyStartupType;

    @Column(name = "KUH_SUB_YN", length = 100)
    private String kuhSubsidiary;

    @Column(name = "INV_SECT_TP", length = 100)
    private String investmentSector;

    @Column(name = "DUDIL_YN", length = 100)
    private String dueDiligence;

    @Column(name = "MAIN_PRDT", length = 1000)
    private String mainProducts;

    @Column(name = "INV_POINT_1", length = 1000)
    private String investmentPoint1;

    @Column(name = "INV_POINT_2", length = 1000)
    private String investmentPoint2;

    @Column(name = "INV_POINT_3", length = 1000)
    private String investmentPoint3;

    @Column(name = "PUB_TEC", length = 100)
    private String publicTechnologyTransfer;

    @Column(name = "SME_YN", length = 100)
    private String smeStatus;

    @Column(name = "LIST_DT", length = 100)
    private String listingDate;

    @Column(name = "LIST_YN", length = 100)
    private String listingStatus;

    @Column(name = "CMP_POST", length = 100)
    private String companyPostalCode;
}
