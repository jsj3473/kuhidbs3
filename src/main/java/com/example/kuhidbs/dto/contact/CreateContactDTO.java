package com.example.kuhidbs.dto.contact;

import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.entity.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateContactDTO {

    private Integer companyId; // 회사 ID
    private String name; // 이름
    private String position; // 직책
    private String email; // 이메일 주소
    private String phoneNumber; // 전화번호

    /**
     * DTO를 Contact 엔터티로 변환
     * @param company 변환 시 사용하는 Company 엔터티
     * @return Contact 엔터티
     */
    public Contact toEntity(Company company) {
        Contact contact = new Contact();
        contact.setCompany(company);
        contact.setName(this.name);
        contact.setPosition(this.position);
        contact.setEmail(this.email);
        contact.setPhoneNumber(this.phoneNumber);
        return contact;
    }
}
