package com.griddynamics.gridquiz.core.services;

import com.griddynamics.gridquiz.api.models.*;
import com.griddynamics.gridquiz.repository.models.UserResult;

import java.util.List;

public interface QuizResultService {

    UserResult calculateResult(List<UserAnswersModel> answers, String userToken);

    void startQuiz(Long quizId, String userToken);

    List<DashboardModel> generateDashboard();

    List<NonApprovedModel> nonApproved();

    List<UserDashboardResultModel> getUsers();

    List<UserResultsModel> getUserResults();
}
