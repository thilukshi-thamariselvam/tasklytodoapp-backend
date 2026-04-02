package com.app2.tasklytodo.controller;

import com.app2.tasklytodo.dto.ApiResponse;
import com.app2.tasklytodo.dto.label.LabelCreateRequest;
import com.app2.tasklytodo.dto.label.LabelResponse;
import com.app2.tasklytodo.service.LabelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LabelResponse>>> getLabels(@RequestParam String userId) {
        log.info("Received request to fetch labels for user: {}", userId);
        List<LabelResponse> responses = labelService.getLabelsByUser(userId);
        return ApiResponse.success("Labels retrieved successfully", responses);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LabelResponse>> createLabel(@Valid @RequestBody LabelCreateRequest request) {
        log.info("Received request to create label: {}", request.getName());
        LabelResponse response = labelService.createLabel(request);
        return ApiResponse.created("Label created successfully", response);
    }
}
