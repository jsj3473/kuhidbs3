package com.example.kuhidbs.dto.company.기본정보;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UCmpInfDTO {
    private String companyId;

    private String companyName;

    private String ceoName;

    private String companyAddress; //기업주소

    private String companyPostalCode; //기업우편번호

    private String listingDate; //상장일자

    private String listingStatus; //상장여부
}
