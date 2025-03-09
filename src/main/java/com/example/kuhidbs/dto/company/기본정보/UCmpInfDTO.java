package com.example.kuhidbs.dto.company.기본정보;
import com.example.kuhidbs.dto.company.kuh투자.RIvtDTO;
import com.example.kuhidbs.dto.company.사후관리.RMngDTO;
import com.example.kuhidbs.dto.company.주주명부.RShrDTO;
import com.example.kuhidbs.dto.company.재무.RFncDTO;
import com.example.kuhidbs.dto.company.투자상태.RStatusDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UCmpInfDTO {
    private String companyId;

    private String companyName;

    private String establishmentDate; //설립일자

    private String ceoName;

    private String companyAddress;

    private String companyPostalCode; //회사우편번호

    private String businessRegistrationNumber;

    private String corporateRegistrationNumber;

    private String industryCode;

    private Integer capital;

    private Integer parValue;

    private Integer employeeCount;

    private String startupType;

    private String regionalCompany;

    private String kuhStartup;

    private String ventureRecognition;

    private String researchRecognition;

    private String earlyStartupType;
    private String isDaechang; //대창창업기업

    private String kuhSubsidiary; //kuh 자회사

    private String investmentSector; //biz sector 구분

    private String dueDiligence; //투자기업실사

    private String mainProducts;

    private String investmentPoint1;

    private String investmentPoint2;

    private String investmentPoint3;

    private String publicTechnologyTransfer;

    private String smeStatus; //중소기업여부

    private String listingDate;

    private String listingStatus;

}
