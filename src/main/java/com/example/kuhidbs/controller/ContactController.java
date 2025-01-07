package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.contact.CreateContactDTO;
import com.example.kuhidbs.entity.Contact;
import com.example.kuhidbs.service.ContactService;
import com.example.kuhidbs.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private CompanyService companyService;

    /**
     * Contact 생성
     * @param createContactDTO Contact 데이터
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody CreateContactDTO createContactDTO) {
        Contact contact = createContactDTO.toEntity(companyService.getCompanyById(createContactDTO.getCompanyId()));
        contactService.createContact(contact);
        return ResponseEntity.ok("Contact created successfully.");
    }
}
