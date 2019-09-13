package com.griddynamics.gridquiz.core.services;

import com.griddynamics.gridquiz.repository.models.User;

public interface AuthenticationService {
    User authorize(User user);
}
