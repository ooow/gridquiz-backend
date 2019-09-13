package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionDao extends MongoRepository<Question, Long> {}
