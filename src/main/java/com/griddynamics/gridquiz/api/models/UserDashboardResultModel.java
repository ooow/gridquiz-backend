package com.griddynamics.gridquiz.api.models;

import com.griddynamics.gridquiz.repository.models.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserDashboardResultModel {
    private User user;
    private List<DashboardModel.DashboardResultModel> results;
}
