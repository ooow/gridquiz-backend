package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.UserInternal;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInternal, String> {
    Optional<UserInternal> findByEmail(String email);

    Optional<UserInternal> findByPhone(String phone);
}
