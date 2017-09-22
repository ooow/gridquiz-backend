package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerDao extends CrudRepository<Answer, Long> {

    Answer findByTextFieldAnswer(String textFieldAnswer);
}
