package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {
    private String userId;
    private String quizId;
    private long seconds;
    private int points;
    private int outOf;
    private int place;
    private boolean isHighlighted;

    public ResultModel(Result result) {
        this.userId = result.getUserId();
        this.quizId = result.getQuizId();
        this.seconds = result.getSeconds();
        this.points = result.getPoints();
        this.outOf = result.getOutOf();
    }
}
