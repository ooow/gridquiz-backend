package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findByPhone(String phone);

    User findByToken(String token);

    List<User> findAllByRole(Role role);
}
