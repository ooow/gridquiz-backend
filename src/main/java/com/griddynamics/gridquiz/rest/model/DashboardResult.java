package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Result;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResult {
    private MiniQuiz miniQuiz;
    private List<Result> top5results;
    private Result currentUserResult;
    private int currentUserPlace;
}
