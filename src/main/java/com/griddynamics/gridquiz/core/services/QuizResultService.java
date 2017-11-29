package com.griddynamics.gridquiz.core.services;

import com.griddynamics.gridquiz.api.models.common.NonApprovedModel;
import com.griddynamics.gridquiz.api.models.dashboard.DashboardModel;
import com.griddynamics.gridquiz.api.models.user.UserAnswersModel;
import com.griddynamics.gridquiz.api.models.user.UserDashboardResultModel;
import com.griddynamics.gridquiz.api.models.user.UserResultsModel;
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
