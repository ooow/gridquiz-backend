package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {}
