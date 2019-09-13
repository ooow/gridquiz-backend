package com.griddynamics.gridquiz.api.models.common;

import com.griddynamics.gridquiz.repository.models.Color;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class MiniQuizzesModel {
    private String id;
    private String name;
    private String description;
    private List<Color> colors;
    private int questionsSize;
    private int questionsComplete;
    private boolean attempt;
}
