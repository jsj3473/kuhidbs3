package com.example.kuhidbs.controller;

import com.example.kuhidbs.dto.company.ReviewerDTO;
import com.example.kuhidbs.service.company.ReviewerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
@RequiredArgsConstructor
public class ReviewerController {

    private final ReviewerService reviewerService;

    /**
     * 리뷰어 생성
     *
     * @param reviewerDTOList 리뷰어 DTO 리스트
     * @return 처리 결과 메시지
     */
    @PostMapping
    public ResponseEntity<String> createReviewers(@RequestBody List<ReviewerDTO> reviewerDTOList) {
        String result = reviewerService.createReviewers(reviewerDTOList);
        return ResponseEntity.ok(result);
    }
}
