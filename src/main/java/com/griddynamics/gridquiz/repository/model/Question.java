package com.griddynamics.gridquiz.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "correctAnswer")
public class Question {
    @Id
    private String id;
    private String text;
    private String code;
    private List<String> answers;
    @JsonIgnore
    private String correctAnswer;
}
