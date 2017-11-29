package com.griddynamics.gridquiz.api.models.user;

import lombok.Data;

@Data
public class UserAnswersModel {
    private Long quizId;
    private Long questionId;
    private String answer;
}
