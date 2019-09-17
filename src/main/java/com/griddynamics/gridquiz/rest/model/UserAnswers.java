package com.griddynamics.gridquiz.rest.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAnswers {
    private String quizId;
    /** Map represents a collections of questionId and user answers. */
    private List<Answer> answers;

    @Data
    public static class Answer {
        private String questionId;
        private String answer;
    }
}
