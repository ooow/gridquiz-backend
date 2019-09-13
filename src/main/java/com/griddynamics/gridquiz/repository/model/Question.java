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
    private String title;
    private List<String> answers;
    private QuestionType type = QuestionType.TEXT;
    @JsonIgnore
    private String correctAnswer;

    // TODO: Make it private.
    public enum QuestionType {
        TEXT,
        CODE,
        INPUT
    }
}
