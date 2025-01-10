package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.ContactDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Contact;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Transactional
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    public String createContacts(List<ContactDTO> contactDTOList) {
        try {
            if (contactDTOList == null || contactDTOList.isEmpty()) {
                return "No contacts provided.";
            }

            // DTO 리스트에서 companyId 추출 (모두 동일한 값이어야 함)
            Integer companyId = contactDTOList.getFirst().getCompanyId();


            // Company 조회
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));

            // 기존 Contact 데이터 삭제
            contactRepository.deleteByCompanyId(companyId);

            // 새로운 Contact 데이터 삽입
            List<Contact> contacts = contactDTOList.stream()
                    .map(dto -> {
                        Contact contact = dto.toEntity(company);
                        return contact;
                    })
                    .collect(Collectors.toList());
            contactRepository.saveAll(contacts);

            return "Contacts have been successfully updated for company ID " + companyId + ".";
        } catch (IllegalArgumentException e) {
            return "Failed to update contacts: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }
    public List<ContactDTO> getAllContactsByCompanyID(Integer companyId) {
        List<Contact> contacts = contactRepository.findByCompany_companyId(companyId);

        // Contact 엔터티를 ContactDTO로 변환
        return contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setCompanyId(contact.getCompany().getCompanyId());
        dto.setName(contact.getName());
        dto.setPosition(contact.getPosition());
        dto.setEmail(contact.getEmail());
        dto.setPhoneNumber(contact.getPhoneNumber());
        return dto;
    }

}
