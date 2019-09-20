package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Quiz;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Progress {
    private Quiz quiz;
    private LocalDateTime start;
}
