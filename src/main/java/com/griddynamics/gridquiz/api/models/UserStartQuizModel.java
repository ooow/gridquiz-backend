package com.griddynamics.gridquiz.api.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserStartQuizModel {
    private Long quizId;
    private String userToken;
}
