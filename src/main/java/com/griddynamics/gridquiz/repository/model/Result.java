package com.griddynamics.gridquiz.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String userId;
    private String quizId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long points;
    private boolean approved;
}
