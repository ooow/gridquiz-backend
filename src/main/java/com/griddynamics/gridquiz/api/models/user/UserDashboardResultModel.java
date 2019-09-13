package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.api.models.dashboard.DashboardResultModel;
import com.griddynamics.gridquiz.repository.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDashboardResultModel {
    private User user;
    private List<DashboardResultModel> results;
}
