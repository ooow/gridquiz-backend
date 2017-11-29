package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Color;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ColorDao extends CrudRepository<Color, Long> {
}
