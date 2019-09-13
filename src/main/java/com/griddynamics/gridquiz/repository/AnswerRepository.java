package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    Answer findByTextFieldAnswer(String textFieldAnswer);
}
