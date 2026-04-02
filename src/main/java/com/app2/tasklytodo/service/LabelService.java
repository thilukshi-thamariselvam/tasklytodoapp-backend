package com.app2.tasklytodo.service;

import com.app2.tasklytodo.dto.label.LabelCreateRequest;
import com.app2.tasklytodo.dto.label.LabelResponse;

import java.util.List;

public interface LabelService {
    List<LabelResponse> getLabelsByUser(String userId);
    LabelResponse createLabel(LabelCreateRequest request);
}
