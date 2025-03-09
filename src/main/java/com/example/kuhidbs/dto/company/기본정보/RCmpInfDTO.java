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

public class RCmpInfDTO {
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

    private String evaluationEmployee;

    private String publicTechnologyTransfer;

    private String smeStatus; //중소기업여부

    private String listingDate;

    private String listingStatus;


    // 최근 2개의 재무제표 정보
    private List<RFncDTO> recentFinancials;

    //kuh 투자내역
    private RIvtDTO rivtDTO;

    //주주명부
    private RShrDTO shrDTO;
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

    //사후관리 표시 manage테이블에서 가져옴
    private RMngDTO rmngDTO;

    //후속투자 테이블에서 가져온 가장 최신의 기업가치
    private Long currentCompanyValue;

    //투자상태
    private RStatusDTO currentStatusDTO;
}
