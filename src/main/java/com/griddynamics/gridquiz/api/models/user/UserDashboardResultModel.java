package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.api.models.dashboard.DashboardResultModel;
import com.griddynamics.gridquiz.repository.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDashboardResultModel {
    private User user;
    private List<DashboardResultModel> results;
}
