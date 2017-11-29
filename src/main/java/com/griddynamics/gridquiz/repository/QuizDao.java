package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Quiz;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface QuizDao extends CrudRepository<Quiz, Long> {
    Quiz findByName(String name);
}
