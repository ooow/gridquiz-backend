package com.griddynamics.gridquiz.api.models.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResultModel {
    private String position;
    private String name;
    private String result;
    private String time;
}
