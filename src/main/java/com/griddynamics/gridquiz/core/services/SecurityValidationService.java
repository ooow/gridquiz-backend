package com.griddynamics.gridquiz.core.services;

public interface SecurityValidationService {
    boolean canStartQuiz(Long quizId, String token);

    boolean validateToken(String userToken);
}
