package com.griddynamics.gridquiz.api.models;

import com.griddynamics.gridquiz.repository.models.Color;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MiniQuizzesModel {

    private Long id;

    private String name;

    private String description;

    private List<Color> colors;

    private int questionsSize;

    private int questionsComplete;

    private boolean attempt;

    public Long getId() {
        return id;
    }
}
