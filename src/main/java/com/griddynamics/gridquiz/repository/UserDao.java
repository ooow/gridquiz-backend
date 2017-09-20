package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findByPhone(String phone);
}
