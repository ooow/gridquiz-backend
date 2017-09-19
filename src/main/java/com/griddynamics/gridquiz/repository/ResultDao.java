package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.data.repository.CrudRepository;

public interface ResultDao extends CrudRepository<UserResult, Long> {
}
