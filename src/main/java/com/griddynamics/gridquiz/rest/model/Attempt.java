package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attempt {
    private Quiz quiz;
    private Result result;
}
