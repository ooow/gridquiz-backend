package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {}
