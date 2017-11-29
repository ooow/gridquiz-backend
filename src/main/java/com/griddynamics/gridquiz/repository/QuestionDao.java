package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Question;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface QuestionDao extends CrudRepository<Question, Long> {
}
