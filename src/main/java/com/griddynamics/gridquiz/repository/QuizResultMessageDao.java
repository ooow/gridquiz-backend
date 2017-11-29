package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface QuizResultMessageDao extends JpaRepository<QuizResultMessage, Long> {
    List<QuizResultMessage> findByQuizId(Long quizId);
}
