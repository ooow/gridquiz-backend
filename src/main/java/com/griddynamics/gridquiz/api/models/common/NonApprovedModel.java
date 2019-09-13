package com.griddynamics.gridquiz.api.models.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NonApprovedModel {
    private String id;
    private String name;
    private int points;
    private String quizName;
}
