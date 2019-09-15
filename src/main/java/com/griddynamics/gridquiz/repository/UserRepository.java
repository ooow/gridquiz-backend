package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
}
