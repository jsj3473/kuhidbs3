package com.example.kuhidbs.dto.company;

import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Reviewer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewerDTO {

    private Integer companyId; // 회사 ID
    private String name; // 이름
    private Double share; // 지분 (백분율)
    private String type; // 발굴자 또는 심사자 또는 사후관리자
    private String appointmentDate; // 임명일자

    public Reviewer toEntity(Company company) {
        Reviewer reviewer = new Reviewer();
        reviewer.setCompany(company);
        reviewer.setName(this.name);
        reviewer.setShare(this.share);
        reviewer.setType(this.type);
        reviewer.setAppointmentDate(this.appointmentDate);
        return reviewer;
    }
}
