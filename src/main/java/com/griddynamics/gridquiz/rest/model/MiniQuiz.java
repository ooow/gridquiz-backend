package com.griddynamics.gridquiz.rest.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiniQuiz {
    private String id;
    private String name;
    private String description;
    private List<String> colors;
    private int questionsSize;
    private int questionsComplete;
    private boolean attempt;
}
