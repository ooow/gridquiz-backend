package com.griddynamics.gridquiz.api.models.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardModel {
    private String quizName;
    private List<DashboardResultModel> results;
}
