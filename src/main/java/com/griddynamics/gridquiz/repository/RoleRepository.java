package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRole(Role.Enum role);
}
