package com.example.kuhidbs.dto.followup;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.Followup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateFollowupDTO {

    private Integer companyId; // 회사 ID
    private Date investmentDate; // 투자일자
    private String investmentInstitution; // 투자기관
    private String fundName; // 펀드명
    private String investmentMethod; // 투자방법
    private BigInteger investmentUnitPrice; // 투자단가
    private BigInteger acquisitionAmount; // 투자금액
    private BigDecimal acquisitionRatio; // 인수지분율
    private BigInteger enterpriseValueAtTime; // 투자 당시 기업 가치

    // DTO를 엔터티로 변환
    public Followup toEntity(Company company) {
        Followup followup = new Followup();
        followup.setCompany(company); // Company 엔터티 주입
        followup.setInvestmentDate(this.investmentDate);
        followup.setInvestmentInstitution(this.investmentInstitution);
        followup.setFundName(this.fundName);
        followup.setInvestmentMethod(this.investmentMethod);
        followup.setInvestmentUnitPrice(this.investmentUnitPrice);
        followup.setAcquisitionAmount(this.acquisitionAmount);
        followup.setAcquisitionRatio(this.acquisitionRatio);
        followup.setEnterpriseValueAtTime(this.enterpriseValueAtTime);
        return followup;
    }
}
