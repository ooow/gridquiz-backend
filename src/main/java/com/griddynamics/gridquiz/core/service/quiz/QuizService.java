package com.griddynamics.gridquiz.core.service.quiz;

import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import java.util.List;

public interface QuizService {
    List<MiniQuizModel> getAllMiniQuizzes();

    List<MiniQuizModel> getUserMiniQuizzes(String userId);

    //    List<DashboardModel> generateDashboard();
    //
    //    List<NonApprovedModel> nonApproved();
    //
    //    List<UserDashboardResultModel> getUsers();
    //
    //    List<UserResultsModel> getUserResults();
}
