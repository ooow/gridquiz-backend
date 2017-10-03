package com.griddynamics.gridquiz.api.models;

import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserResultsModel {
    private User user;
    private List<UserResult> results;
}
