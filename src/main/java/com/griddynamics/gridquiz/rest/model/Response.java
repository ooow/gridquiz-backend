package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
    private User user;
    private T message;
}
