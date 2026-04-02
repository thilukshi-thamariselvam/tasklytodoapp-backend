package com.app2.tasklytodo.service.impl;

import com.app2.tasklytodo.dto.label.LabelCreateRequest;
import com.app2.tasklytodo.dto.label.LabelResponse;
import com.app2.tasklytodo.entity.Label;
import com.app2.tasklytodo.entity.User;
import com.app2.tasklytodo.exception.ResourceNotFoundException;
import com.app2.tasklytodo.repository.LabelRepository;
import com.app2.tasklytodo.repository.UserRepository;
import com.app2.tasklytodo.service.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LabelResponse> getLabelsByUser(String userId) {
        log.info("Fetching labels for user: {}", userId);
        List<Label> labels = labelRepository.findByUserIdOrderByCreatedAtDesc(Long.valueOf(userId));
        return labels.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public LabelResponse createLabel(LabelCreateRequest request) {
        log.info("Creating label: {} for user: {}", request.getName(), request.getUserId());

        User user = userRepository.findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Label label = Label.builder()
                .name(request.getName())
                .color(request.getColor())
                .user(user)
                .build();

        Label savedLabel = labelRepository.save(label);
        return toResponse(savedLabel);
    }

    private LabelResponse toResponse(Label label) {
        return LabelResponse.builder()
                .id(label.getId())
                .name(label.getName())
                .color(label.getColor())
                .createdAt(label.getCreatedAt())
                .updatedAt(label.getUpdatedAt())
                .build();
    }
}
