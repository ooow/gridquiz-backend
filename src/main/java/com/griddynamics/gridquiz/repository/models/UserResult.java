package com.griddynamics.gridquiz.repository.models;

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
public class UserResult {
    @Id
    private String id;

    private User user;

    @JsonIgnore
    private Quiz quiz;

    private int points;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean approved;

    private QuizResultMessage comment;
}
