package com.griddynamics.gridquiz.repository.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultMessage {
    @Id
    @JsonIgnore
    private String id;

    @JsonIgnore
    private Quiz quiz;

    private int rate;

    private String message;
}
