package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniQuizModel {
    private String id;
    private String name;
    private String description;
    private String color;
    private int questionsSize;
    private int questionsComplete;
    private boolean attempt;

    public MiniQuizModel(Quiz quiz) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.description = quiz.getDescription();
        this.color = quiz.getColor();
        this.questionsSize = quiz.getQuestions().size();
    }
}
