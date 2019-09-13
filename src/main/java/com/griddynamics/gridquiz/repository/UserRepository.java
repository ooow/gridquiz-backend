package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.auth.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<List<User>> findAllByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
}
