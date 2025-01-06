package com.example.kuhidbs.dto.company;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

public class CreateCompanyDTO {

    private String companyName;
    private String address;
    private String businessId;
    private String corporateId;
    private String ceoName;
    private String industryCode;
    private Date establishedDate;
    private String industry;
    private String businessItem;
    private String founderCarrerType; // Enum 대신 문자열로 받음
    private String founderUnivType;  // Enum 대신 문자열로 받음
    private Set<String> certifications; // 중복 선택 가능한 문자열 Set
    private BigInteger capital;
    private BigInteger faceValue;
    private String investmentStage;
    private String enterpriseValue;
    private Integer fourInsuranceMembers;

    // Getters and Setters

    @Override
    public String toString() {
        return "CreateCompanyDTO{" +
                "companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", businessId='" + businessId + '\'' +
                ", corporateId='" + corporateId + '\'' +
                ", ceoName='" + ceoName + '\'' +
                ", industryCode='" + industryCode + '\'' +
                ", establishedDate=" + establishedDate +
                ", industry='" + industry + '\'' +
                ", businessItem='" + businessItem + '\'' +
                ", founderCarrerType='" + founderCarrerType + '\'' +
                ", founderUnivType='" + founderUnivType + '\'' +
                ", certifications=" + certifications +
                ", capital=" + capital +
                ", faceValue=" + faceValue +
                ", investmentStage='" + investmentStage + '\'' +
                ", enterpriseValue='" + enterpriseValue + '\'' +
                ", fourInsuranceMembers=" + fourInsuranceMembers +
                '}';
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

    public CreateCompanyDTO(String address) {
        this.address = address;
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

    public String getFounderCarrerType() {
        return founderCarrerType;
    }

    public void setFounderCarrerType(String founderCarrerType) {
        this.founderCarrerType = founderCarrerType;
    }

    public String getFounderUnivType() {
        return founderUnivType;
    }

    public void setFounderUnivType(String founderUnivType) {
        this.founderUnivType = founderUnivType;
    }

    public Set<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<String> certifications) {
        this.certifications = certifications;
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
