package com.example.kuhidbs.dto.company.cmpinf;
import com.example.kuhidbs.dto.company.fnc.RFncDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RCmpInf2DTO {
    private String companyId;

    private String companyName;

    private String establishmentDate; //설립일자

    private String ceoName;

    private String companyAddress;

    private String companyPostalCode; //회사우편번호

    private String businessRegistrationNumber; //사업자등록번호

    private String corporateRegistrationNumber; //법인등록번호

    private String industryCode;

    private Integer capital;

    private Integer parValue; //액면가

    private Integer employeeCount; //임직원수

    private String kuhSubsidiary; //kuh 자회사

    private String kuhStartup; //고대창업구분

    private String startupType; //창업구분

    private String earlyStartupType; //초기창업구분

    private String smeStatus; //중소기업여부

    private String regionalCompany;  //지방창업구분

    private String isDaechang; //대창창업기업

    private String ventureRecognition; //벤처기업구분

    private String researchRecognition; //연구소기업구분

    private String publicTechnologyTransfer; //공공기술이전

    private String pubTechCommercial; //공공연구기관 기술활용 사업화 기업

    private String dueDiligence; //투자기업실사

    private String listingStatus; //상장여부

    private String listingDate; //상장일자

    private String investmentSector; //biz sector 구분

    private String mainProducts; //주요제품내용

    private String investmentPoint1;

    private String investmentPoint2;

    private String investmentPoint3;


    // 최근 2개의 재무제표 정보
    private List<RFncDTO> recentFinancials;
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
