package com.griddynamics.gridquiz.api.models.common;

import com.griddynamics.gridquiz.repository.models.Color;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MiniQuizzesModel {
    private Long id;
    private String name;
    private String description;
    private List<Color> colors;
    private int questionsSize;
    private int questionsComplete;
    private boolean attempt;
}
