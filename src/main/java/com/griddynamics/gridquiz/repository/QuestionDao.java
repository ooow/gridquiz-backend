package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionDao extends CrudRepository<Question, Long> {
}
