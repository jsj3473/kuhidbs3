package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "business_id", length = 20, unique = true)
    private String businessId;

    @Column(name = "corporate_id", length = 20, unique = true)
    private String corporateId;

    @Column(name = "ceo_name", length = 50)
    private String ceoName;

    @Column(name = "industry_code", length = 50)
    private String industryCode;

    @Temporal(TemporalType.DATE)
    @Column(name = "established_date")
    private Date establishedDate;

    @Column(name = "industry", length = 50)
    private String industry;

    @Column(name = "business_item", columnDefinition = "TEXT")
    private String businessItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "startup_type", length = 20)
    private StartupType startupType;

    @Enumerated(EnumType.STRING)
    @Column(name = "certification_type", length = 20)
    private CertificationType certificationType;

    @Column(name = "capital", precision = 19, scale = 0)
    private BigInteger capital;

    @Column(name = "face_value", precision = 19, scale = 0)
    private BigInteger faceValue;

    @Column(name = "investment_stage", length = 50)
    private String investmentStage;

    @Column(name = "enterprise_value", length = 50)
    private String enterpriseValue;

    @Column(name = "four_insurance_members", nullable = true)
    private Integer fourInsuranceMembers;

    // Enum 클래스 정의
    public enum StartupType {
        TYPE1, TYPE2, TYPE3 // 실제 타입에 따라 수정
    }

    public enum CertificationType {
        CERTIFIED, NOT_CERTIFIED // 실제 타입에 따라 수정
    }

    // Getters and Setters
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Date establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBusinessItem() {
        return businessItem;
    }

    public void setBusinessItem(String businessItem) {
        this.businessItem = businessItem;
    }

    public StartupType getStartupType() {
        return startupType;
    }

    public void setStartupType(StartupType startupType) {
        this.startupType = startupType;
    }

    public CertificationType getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    public BigInteger getCapital() {
        return capital;
    }

    public void setCapital(BigInteger capital) {
        this.capital = capital;
    }

    public BigInteger getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigInteger faceValue) {
        this.faceValue = faceValue;
    }

    public String getInvestmentStage() {
        return investmentStage;
    }

    public void setInvestmentStage(String investmentStage) {
        this.investmentStage = investmentStage;
    }

    public String getEnterpriseValue() {
        return enterpriseValue;
    }

    public void setEnterpriseValue(String enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }

    public Integer getFourInsuranceMembers() {
        return fourInsuranceMembers;
    }

    public void setFourInsuranceMembers(Integer fourInsuranceMembers) {
        this.fourInsuranceMembers = fourInsuranceMembers;
    }
}
