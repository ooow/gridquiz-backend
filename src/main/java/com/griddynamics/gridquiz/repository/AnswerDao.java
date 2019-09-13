package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerDao extends MongoRepository<Answer, Long> {
    Answer findByTextFieldAnswer(String textFieldAnswer);
}
