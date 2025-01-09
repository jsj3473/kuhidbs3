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

    @Column(name = "business_id", length = 20)
    private String businessId; // 사업자등록번호

    @Column(name = "corporate_id", length = 20)
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

    @Column(name = "founder_carrer_type", length = 20)
    private String founderCarrerType; // 창업형태-소속

    @Column(name = "founder_univ_type", length = 20)
    private String founderUnivType; // 창업형태-설립자

    @Column(name = "certification_type", length = 20)
    private String certificationType; // 인증기업구분

    @Column(name = "is_early_stage_startup")
    private Boolean isEarlyStageStartup = false; // 초기창업기업 여부

    @Column(name = "capital", precision = 19, scale = 0)
    private BigInteger capital; // 설립자본금

    @Column(name = "face_value", precision = 19, scale = 0)
    private BigInteger faceValue; // 액면가

    @Column(name = "investment_stage", length = 50)
    private String investmentStage; // 투자단계

    @Column(name = "investment_value", precision = 19, scale = 2)
    private BigInteger investmentValue; // 투자밸류

    @Temporal(TemporalType.DATE)
    @Column(name = "investment_date")
    private Date investmentDate; // 투자일자

    @Column(name = "investment_funding", length = 100)
    private String investmentFunding; // 투자재원

    @Column(name = "investment_method", length = 50)
    private String investmentMethod; // 투자방법

    @Column(name = "cash_investment_price", precision = 19, scale = 2)
    private BigInteger cashInvestmentPrice; // 투자금액-현금

    @Column(name = "non_cash_investment_price", precision = 19, scale = 2)
    private BigInteger nonCashInvestmentPrice; // 투자금액-현물

    @Column(name = "cash_investment_unit")
    private BigInteger cashInvestmentUnit; //투자단가-현금

    @Column(name = "non_cash_investment_unit")
    private BigInteger nonCashInvestmentUnit; //투자단가-현물

    @Column(name = "initial_investment_share", precision = 5, scale = 2)
    private BigDecimal initialInvestmentShare; // 최초투자지분율

    @Column(name = "investment_product", columnDefinition = "TEXT")
    private String investmentProduct; // 투자상품

    @Column(name = "acquisition_cost")
    private BigInteger acquisitionCost; // 인수주식수

    @Column(name = "tips_support", length = 50)
    private String tipsSupport; // TIPS 여부

    @Column(name = "is_listed")
    private Boolean isListed = false; //상장여부

    @Column(name = "listing_market", length = 50)
    private String listingMarket; // 상장시장

    @Column(name = "investment_point1", length = 300)
    private String investmentPoint1; // 투자포인트1

    @Column(name = "investment_point2", length = 300)
    private String investmentPoint2; // 투자포인트2

    @Column(name = "investment_point3", length = 300)
    private String investmentPoint3; // 투자포인트3

    @Temporal(TemporalType.DATE)
    @Column(name = "listing_date")
    private Date listingDate; // 상장일자

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    // Enum 클래스 정의

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date(); // 현재 시간 자동 설정
    }
}
