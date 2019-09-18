package com.griddynamics.gridquiz.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request<T> {
    private UserModel user;
    private T message;
}
