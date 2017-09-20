package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ResultDao extends CrudRepository<UserResult, Long> {
    @Transactional
    List<UserResult> removeByUser(User user);
}
