package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultMessageDao extends JpaRepository<QuizResultMessage, Long> {
    List<QuizResultMessage> findByQuizId(Long quizId);
}
