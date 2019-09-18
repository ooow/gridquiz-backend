package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserRegistered;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResultsModel {
    private UserRegistered user;
    private List<Result> results;
}
