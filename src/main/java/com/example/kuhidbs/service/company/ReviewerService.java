package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.ReviewerDTO;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Reviewer;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;
    private final CompanyRepository companyRepository;

    public ReviewerService(ReviewerRepository reviewerRepository, CompanyRepository companyRepository) {
        this.reviewerRepository = reviewerRepository;
        this.companyRepository = companyRepository;
    }

    public String createReviewers(List<ReviewerDTO> reviewerDTOList) {
        try {
            if (reviewerDTOList == null || reviewerDTOList.isEmpty()) {
                return "No reviewers provided.";
            }

            // DTO 리스트에서 companyId 추출 (모두 동일한 값이어야 함)
            Integer companyId = reviewerDTOList.get(0).getCompanyId();
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));

            // Reviewer 리스트 생성
            List<Reviewer> reviewers = reviewerDTOList.stream()
                    .map(dto -> dto.toEntity(company))
                    .collect(Collectors.toList());

            // 저장
            reviewerRepository.saveAll(reviewers);

            return "Reviewers have been successfully created.";
        } catch (IllegalArgumentException e) {
            return "Failed to create reviewers: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }


    // 특정 CompanyID에 해당하는 Reviewer를 가져오는 메서드
    public List<ReviewerDTO> getAllReviewersByCompanyID(int companyId) {
        List<Reviewer> reviewers = reviewerRepository.findByCompany_companyId(companyId);

        // Reviewer 엔터티를 ReviewerDTO로 변환
        return reviewers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 엔터티를 DTO로 변환하는 메서드
    private ReviewerDTO convertToDTO(Reviewer reviewer) {
        ReviewerDTO dto = new ReviewerDTO();
        dto.setCompanyId(reviewer.getCompany().getCompanyId()); // 회사 ID
        dto.setName(reviewer.getName());
        dto.setShare(reviewer.getShare());
        dto.setType(reviewer.getType());
        dto.setAppointmentDate(reviewer.getAppointmentDate() != null
                ? reviewer.getAppointmentDate().toString()
                : null);
        return dto;
    }
}
