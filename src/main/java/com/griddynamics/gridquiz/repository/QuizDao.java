package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizDao extends CrudRepository<Quiz, Long> {
}
