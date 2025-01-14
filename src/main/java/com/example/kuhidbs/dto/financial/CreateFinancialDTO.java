package com.example.kuhidbs.dto.financial;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.Financial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class CreateFinancialDTO {

    private Integer companyId; // 회사 ID
    private Integer financialYear; // 연도
    private String financialHalfYear; //반기
    private BigInteger revenue; // 매출액
    private BigInteger operatingProfit; // 영업이익
    private BigInteger netIncome; // 당기순이익
    private BigInteger currentAssets; // 자산총계
    private BigInteger currentLiabilities; // 부채총계
    private BigInteger totalEquity; // 자본총계
    private BigInteger capital; // 자본금
    private Integer employees; // 임직원 수
    private Integer foreignPatents; // 해외특허 보유 건수
    private BigInteger equityValue; // 당사 보유 지분가치
    private String overallEvaluation; // 종합평가

    /**
     * DTO를 Financial 엔터티로 변환
     *
     * @param company 변환 시 사용하는 Company 엔터티
     * @return Financial 엔터티
     */
    public Financial toEntity(Company company) {
        Financial financial = new Financial();
        financial.setCompany(company);
        financial.setFinancialYear(this.financialYear);
        financial.setFinancialHalfYear(this.financialHalfYear);
        financial.setRevenue(this.revenue);
        financial.setOperatingProfit(this.operatingProfit);
        financial.setNetIncome(this.netIncome);
        financial.setCurrentAssets(this.currentAssets);
        financial.setCurrentLiabilities(this.currentLiabilities);
        financial.setTotalEquity(this.totalEquity);
        financial.setCapital(this.capital);
        financial.setEmployees(this.employees);
        financial.setForeignPatents(this.foreignPatents);
        financial.setEquityValue(this.equityValue);
        financial.setOverallEvaluation(this.overallEvaluation);
        return financial;
    }
}
