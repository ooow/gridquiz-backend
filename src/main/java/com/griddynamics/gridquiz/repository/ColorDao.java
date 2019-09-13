package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Color;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ColorDao extends MongoRepository<Color, Long> {}
