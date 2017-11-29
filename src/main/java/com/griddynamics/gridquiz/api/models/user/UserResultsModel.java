package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResultsModel {
    private User user;
    private List<UserResult> results;
}
