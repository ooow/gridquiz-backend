package com.griddynamics.gridquiz.api.models.user;

import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserInternal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResultsModel {
    private UserInternal user;
    private List<Result> results;
}
