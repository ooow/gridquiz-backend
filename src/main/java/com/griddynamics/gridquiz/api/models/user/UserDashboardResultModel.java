package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.api.models.dashboard.DashboardResultModel;
import com.griddynamics.gridquiz.repository.model.UserRegistered;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDashboardResultModel {
    private UserRegistered user;
    private List<DashboardResultModel> results;
}
