package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Integer companyId; // 고유 ID (AUTO_INCREMENT)

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName; // 회사명

    @Column(name = "address", columnDefinition = "TEXT")
    private String address; // 기업주소

    @Column(name = "business_id", length = 20, unique = true)
    private String businessId; // 사업자등록번호

    @Column(name = "corporate_id", length = 20, unique = true)
    private String corporateId; // 법인등록번호

    @Column(name = "ceo_name", length = 50)
    private String ceoName; // 대표자

    @Column(name = "industry_code", length = 50)
    private String industryCode; // 표준산업분류코드

    @Temporal(TemporalType.DATE)
    @Column(name = "established_date")
    private Date establishedDate; // 설립일자

    @Column(name = "industry", length = 50)
    private String industry; // 기술분야

    @Column(name = "business_item", columnDefinition = "TEXT")
    private String businessItem; // 사업아이템

    @Enumerated(EnumType.STRING)
    @Column(name = "founder_carrer_type", length = 20)
    private FounderCarrerType founderCarrerType; // 창업형태-소속

    @Enumerated(EnumType.STRING)
    @Column(name = "founder_univ_type", length = 20)
    private FounderUnivType founderUnivType; // 창업형태-설립자

    @Enumerated(EnumType.STRING)
    @Column(name = "certification_type", length = 20)
    private CertificationType certificationType; // 인증기업분류

    @Column(name = "capital", precision = 19, scale = 0)
    private BigInteger capital; // 설립자본금

    @Column(name = "face_value", precision = 19, scale = 0)
    private BigInteger faceValue; // 액면가

    @Column(name = "investment_stage", length = 50)
    private String investmentStage; // 투자단계

    @Column(name = "four_insurance_members")
    private Integer fourInsuranceMembers; // 4대보험가입자수

    @Column(name = "current_company_value", precision = 19, scale = 2)
    private BigDecimal currentCompanyValue; // 현재기업가치

    @Temporal(TemporalType.DATE)
    @Column(name = "investment_date")
    private Date investmentDate; // 투자일자

    @Column(name = "investment_funding", length = 100)
    private String investmentFunding; // 투자재원

    @Column(name = "investment_method", length = 50)
    private String investmentMethod; // 투자방법

    @Column(name = "investment_price", precision = 19, scale = 2)
    private BigDecimal investmentPrice; // 투자금액

    @Column(name = "initial_investment_share", precision = 5, scale = 2)
    private BigDecimal initialInvestmentShare; // 최초투자지분율

    @Column(name = "investment_product", columnDefinition = "TEXT")
    private String investmentProduct; // 투자상품

    @Column(name = "acquisition_cost", precision = 19, scale = 2)
    private BigDecimal acquisitionCost; // 인수주식수

    @Column(name = "tips_support", length = 50)
    private String tipsSupport; // TIPS 여부

    @Column(name = "listing_market", length = 50)
    private String listingMarket; // 상장시장

    @Temporal(TemporalType.DATE)
    @Column(name = "listing_date")
    private Date listingDate; // 상장일자

    @ElementCollection
    @CollectionTable(name = "company_explorers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "explorer_name")
    private List<String> explorers; // 발굴자 (여러 명 가능)

    @ElementCollection
    @CollectionTable(name = "company_reviewers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "reviewer_name")
    private List<String> reviewers; // 심사자 (여러 명 가능)

    @Column(name = "post_manager", length = 50)
    private String postManager; // 사후관리자

    // Enum 클래스 정의
    public enum FounderCarrerType {
        교원창업, 교우창업, 학생창업, 기타
    }

    public enum FounderUnivType {
        고려대, 비고려대, 기타
    }

    public enum CertificationType {
        연구소기업, 초기창업기업, 벤처기업, 기타
    }
}
