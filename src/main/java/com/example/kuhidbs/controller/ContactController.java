package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.ContactDTO;
import com.example.kuhidbs.service.company.ContactService;
import com.example.kuhidbs.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private CompanyService companyService;


    @PostMapping
    public String createContacts(@RequestBody List<ContactDTO> contactDTOList) {
        return contactService.createContacts(contactDTOList);
    }
}
