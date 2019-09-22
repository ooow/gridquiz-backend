package com.griddynamics.gridquiz.rest.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardModel {
    private MiniQuiz miniQuiz;
    private List<ResultModel> results;
}
