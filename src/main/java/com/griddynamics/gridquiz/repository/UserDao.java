package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, Long> {
    User findByEmail(String email);

    User findByPhone(String phone);

    User findByToken(String token);

    List<User> findAllByRole(Role role);
}
