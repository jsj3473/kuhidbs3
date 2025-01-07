package com.example.kuhidbs.dto.company;

import com.example.kuhidbs.entity.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateCompanyDTO {

    private String companyName; // 회사명
    private String address; // 기업주소
    private String businessId; // 사업자등록번호
    private String corporateId; // 법인등록번호
    private String ceoName; // 대표자
    private String industryCode; // 표준산업분류코드
    private Date establishedDate; // 설립일자
    private String industry; // 기술분야
    private String businessItem; // 사업아이템
    private String founderCarrerType; // 창업형태-소속 (Enum 대신 문자열로 받음)
    private String founderUnivType; // 창업형태-설립자 (Enum 대신 문자열로 받음)
    private String certificationType; // 인증기업분류 (Enum 대신 문자열로 받음)
    private BigInteger capital; // 설립자본금
    private BigInteger faceValue; // 액면가
    private String investmentStage; // 투자단계
    private Integer fourInsuranceMembers; // 4대보험가입자수
    private BigDecimal currentCompanyValue; // 현재기업가치
    private Date investmentDate; // 투자일자
    private String investmentFunding; // 투자재원
    private String investmentMethod; // 투자방법
    private BigDecimal investmentPrice; // 투자금액
    private BigDecimal initialInvestmentShare; // 최초투자지분율
    private String investmentProduct; // 투자상품
    private BigDecimal acquisitionCost; // 인수주식수
    private String tipsSupport; // TIPS 여부
    private String listingMarket; // 상장시장
    private Date listingDate; // 상장일자
    private List<String> explorers; // 발굴자 (여러 명 가능)
    private List<String> reviewers; // 심사자 (여러 명 가능)
    private String postManager; // 사후관리자

    // toEntity 메소드 추가
    public Company toEntity() {
        Company company = new Company();
        company.setCompanyName(this.companyName);
        company.setAddress(this.address);
        company.setBusinessId(this.businessId);
        company.setCorporateId(this.corporateId);
        company.setCeoName(this.ceoName);
        company.setIndustryCode(this.industryCode);
        company.setEstablishedDate(this.establishedDate);
        company.setIndustry(this.industry);
        company.setBusinessItem(this.businessItem);

        // Enum 변환
        company.setFounderCarrerType(Company.FounderCarrerType.valueOf(this.founderCarrerType));
        company.setFounderUnivType(Company.FounderUnivType.valueOf(this.founderUnivType));
        company.setCertificationType(Company.CertificationType.valueOf(this.certificationType));

        company.setCapital(this.capital);
        company.setFaceValue(this.faceValue);
        company.setInvestmentStage(this.investmentStage);
        company.setFourInsuranceMembers(this.fourInsuranceMembers);
        company.setCurrentCompanyValue(this.currentCompanyValue);
        company.setInvestmentDate(this.investmentDate);
        company.setInvestmentFunding(this.investmentFunding);
        company.setInvestmentMethod(this.investmentMethod);
        company.setInvestmentPrice(this.investmentPrice);
        company.setInitialInvestmentShare(this.initialInvestmentShare);
        company.setInvestmentProduct(this.investmentProduct);
        company.setAcquisitionCost(this.acquisitionCost);
        company.setTipsSupport(this.tipsSupport);
        company.setListingMarket(this.listingMarket);
        company.setListingDate(this.listingDate);
        company.setExplorers(this.explorers);
        company.setReviewers(this.reviewers);
        company.setPostManager(this.postManager);

        return company;
    }
}
