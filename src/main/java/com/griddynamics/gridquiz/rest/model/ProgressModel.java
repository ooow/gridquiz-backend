package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Quiz;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressModel {
    private Quiz quiz;
    private Instant start;
}
