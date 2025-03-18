package com.example.kuhidbs.dto.company.cmpinf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CCmpInfDTO {

    private String companyId;

    private String companyName;

    private String establishmentDate;

    private String ceoName;

    private String companyAddress;

    private String businessRegistrationNumber;

    private String corporateRegistrationNumber;

    private String industryCode;

    private Integer capital;

    private Integer parValue;

    private Integer employeeCount;

    private String isDaechang;

    private String startupType;

    private String regionalCompany;

    private String kuhStartup;

    private String ventureRecognition;

    private String researchRecognition;

    private String earlyStartupType;

    private String kuhSubsidiary;

    private String investmentSector;

    private String dueDiligence;

    private String mainProducts;

    private String investmentPoint1;

    private String investmentPoint2;

    private String investmentPoint3;

    private String publicTechnologyTransfer;

    private String smeStatus;

    private String listingDate;

    private String listingStatus;

    private String companyPostalCode;


    //재무제표
    private Integer financialYear;

    private String financialHalf;

    private BigDecimal revenue;

    private BigDecimal operatingProfit;

    private BigDecimal netIncome;

    private BigDecimal totalAssets;

    private BigDecimal totalCapital;

    private BigDecimal totalDebt;

    private BigDecimal capitalFnc;

    private Integer employeeCountFnc;

    //발굴자,심사자,사후관리담당자

    //발굴자
    private String manager1;
    //심사자
    private String manager2;
    //사후관리자
    private String manager3;

    //피투자기업 실무자
    private String positionType;
    private String phoneNumber;
    private String email;
    private String name;
}
