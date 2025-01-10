package com.example.kuhidbs.dto.company;

import com.example.kuhidbs.entity.company.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReadCompanyDTO {

    private Integer companyId;
    private String companyName; // 회사명
    private String address; // 기업주소
    private String businessId; // 사업자등록번호
    private String corporateId; // 법인등록번호
    private String ceoName; // 대표자
    private String industryCode; // 표준산업분류코드
    private String establishedDate; // 설립일자
    private String industry; // 기술분야
    private String businessItem; // 사업아이템
    private String founderCarrerType; // 창업형태-소속 (Enum 대신 문자열로 받음)
    private String founderUnivType; // 창업형태-설립자 (Enum 대신 문자열로 받음)
    private String certificationType; // 인증기업분류 (Enum 대신 문자열로 받음)
    private BigInteger capital; // 설립자본금
    private Boolean isEarlyStageStartup; // 초기창업기업 여부
    private BigInteger faceValue; // 액면가
    private String investmentStage; // 투자단계
    private BigInteger investmentValue; // 투자밸류
    private String investmentDate; // 투자일자
    private String investmentFunding; // 투자재원
    private String investmentMethod; // 투자방법
    private BigInteger cashInvestmentPrice; // 투자금액-현금
    private BigInteger nonCashInvestmentPrice; // 투자금액-현물
    private BigInteger cashInvestmentUnit; //투자단가-현금
    private BigInteger nonCashInvestmentUnit; //투자단가-현물
    private BigDecimal initialInvestmentShare; // 최초투자지분율
    private String investmentProduct; // 투자상품
    private BigInteger acquisitionCost; // 인수주식수
    private String tipsSupport; // TIPS 여부
    private Boolean isListed; //상장여부
    private String listingMarket; // 상장시장
    private String listingDate; // 상장일자
    private String investmentPoint1; // 투자포인트1
    private String investmentPoint2; // 투자포인트2
    private String investmentPoint3; // 투자포인트3


    public Company toEntity() {
        Company company = new Company();

        company.setCompanyName(this.companyName);
        company.setAddress(this.address);
        company.setBusinessId(this.businessId);
        company.setCorporateId(this.corporateId);
        company.setCeoName(this.ceoName);
        company.setIndustryCode(this.industryCode);
        company.setIndustry(this.industry);
        company.setBusinessItem(this.businessItem);
        company.setFounderCarrerType(this.founderCarrerType);
        company.setFounderUnivType(this.founderUnivType);
        company.setCertificationType(this.certificationType);
        company.setEstablishedDate(this.establishedDate);
        company.setInvestmentDate(this.investmentDate);
        company.setListingDate(this.listingDate);

        // 기타 필드
        company.setCapital(this.capital);
        company.setIsEarlyStageStartup(this.isEarlyStageStartup);
        company.setFaceValue(this.faceValue);
        company.setInvestmentStage(this.investmentStage);
        company.setInvestmentValue(this.investmentValue);
        company.setInvestmentFunding(this.investmentFunding);
        company.setInvestmentMethod(this.investmentMethod);
        company.setCashInvestmentPrice(this.cashInvestmentPrice);
        company.setNonCashInvestmentPrice(this.nonCashInvestmentPrice);
        company.setCashInvestmentUnit(this.cashInvestmentUnit);
        company.setNonCashInvestmentUnit(this.nonCashInvestmentUnit);
        company.setInitialInvestmentShare(this.initialInvestmentShare);
        company.setInvestmentProduct(this.investmentProduct);
        company.setAcquisitionCost(this.acquisitionCost);
        company.setTipsSupport(this.tipsSupport);
        company.setIsListed(this.isListed);
        company.setListingMarket(this.listingMarket);
        company.setInvestmentPoint1(this.investmentPoint1);
        company.setInvestmentPoint2(this.investmentPoint2);
        company.setInvestmentPoint3(this.investmentPoint3);

        return company;
    }


}
