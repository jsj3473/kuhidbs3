package com.example.kuhidbs.dto.recovery;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.Recovery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateRecoveryDTO {

    private Integer companyId; // 회사 ID
    private Date recoveryDate; // 회수 일자
    private Integer recoveryUnitPrice; // 회수 단가
    private String investmentMethod; // 회수 방법
    private BigInteger recoveryAmount; // 회수 수익금
    private BigDecimal recoveryRatio; // 회수 지분율
    private BigInteger recoveryIncome; // 회수 수입금
    private BigInteger remainingInvestment; // 투자 잔액
    private BigInteger investmentReduction; // 투자 감액

    /**
     * DTO를 Recovery 엔터티로 변환
     *
     * @param company 변환 시 사용하는 Company 엔터티
     * @return Recovery 엔터티
     */
    public Recovery toEntity(Company company) {
        Recovery recovery = new Recovery();
        recovery.setCompany(company);
        recovery.setRecoveryDate(this.recoveryDate);
        recovery.setRecoveryUnitPrice(this.recoveryUnitPrice);
        recovery.setInvestmentMethod(this.investmentMethod);
        recovery.setRecoveryAmount(this.recoveryAmount);
        recovery.setRecoveryRatio(this.recoveryRatio);
        recovery.setRecoveryIncome(this.recoveryIncome);
        recovery.setRemainingInvestment(this.remainingInvestment);
        recovery.setInvestmentReduction(this.investmentReduction);
        return recovery;
    }
}
