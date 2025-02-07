package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.기본정보.CCmpInfDTO;
import com.example.kuhidbs.entity.company.Client;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.repository.company.ClientRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;

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
