package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.UserRegistered;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserRegistered, String> {
    Optional<UserRegistered> findByEmail(String email);

    Optional<UserRegistered> findByPhone(String phone);
}
