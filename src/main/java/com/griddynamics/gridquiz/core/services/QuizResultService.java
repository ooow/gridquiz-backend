package com.griddynamics.gridquiz.core.services;

import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.repository.models.Result;

import java.util.List;

public interface QuizResultService {

    Result calculateResult(List<UserAnswersModel> answers);
}
