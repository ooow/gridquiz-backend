package com.griddynamics.gridquiz.core.services;

public interface SecurityValidationService {
    void canStartQuiz(Long quizId, String token);

    void validateToken(String userToken);

    void isAdmin(String userToken);
}
