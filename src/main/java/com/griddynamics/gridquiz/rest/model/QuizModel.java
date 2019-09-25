package com.griddynamics.gridquiz.rest.model;

import static java.util.stream.Collectors.toList;

import com.griddynamics.gridquiz.repository.model.Question;
import com.griddynamics.gridquiz.repository.model.Quiz;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizModel {
    private String name;
    private String description;
    private String color;
    private List<QuestionModel> questions;

    public Quiz toObject() {
        return Quiz.builder()
                .name(name)
                .description(description)
                .color(color)
                .questions(questions.stream().map(QuestionModel::toObject).collect(toList()))
                .build();
    }

    @Data
    @Builder
    static class QuestionModel {
        private String text;
        private String code;
        private List<String> answers;
        private String correctAnswer;

        Question toObject() {
            return Question.builder()
                    .text(text)
                    .code(code)
                    .answers(answers)
                    .correctAnswer(correctAnswer)
                    .build();
        }
    }
}
