package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizResultMessageDao extends MongoRepository<QuizResultMessage, Long> {
    List<QuizResultMessage> findByQuizId(String quizId);
}
