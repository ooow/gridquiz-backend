package com.griddynamics.gridquiz.repository.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    private String id;
    private String userId;
    private String quizId;
    private Instant startTime;
    private Instant endTime;
    private long seconds;
    private int points;
    private int outOf;
}
