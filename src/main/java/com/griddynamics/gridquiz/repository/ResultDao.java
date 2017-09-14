package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Result;
import org.springframework.data.repository.CrudRepository;

public interface ResultDao extends CrudRepository<Result, Long> {
}
