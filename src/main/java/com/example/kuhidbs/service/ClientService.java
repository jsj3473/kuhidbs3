package com.example.kuhidbs.service;

import com.example.kuhidbs.dto.CCmpInfDTO;
import com.example.kuhidbs.entity.Client;
import com.example.kuhidbs.entity.Company;
import com.example.kuhidbs.repository.ClientRepository;
import com.example.kuhidbs.repository.CmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CmpRepository companyRepository;

    public void saveClient(CCmpInfDTO CCmpInfDTO) {

        // Company 객체 조회
        Company company = companyRepository.findById(CCmpInfDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + CCmpInfDTO.getCompanyId()));


        Client client = Client.builder()
                .company(company)
                .positionType(CCmpInfDTO.getPositionType())
                .phoneNumber(CCmpInfDTO.getPhoneNumber())
                .email(CCmpInfDTO.getEmail())
                .name(CCmpInfDTO.getName())
                .build();

        clientRepository.save(client);
    }
}
