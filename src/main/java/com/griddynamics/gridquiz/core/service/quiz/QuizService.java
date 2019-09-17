package com.griddynamics.gridquiz.core.service.quiz;

import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import java.util.List;

public interface QuizService {
    List<MiniQuiz> getAllMiniQuizzes();

    List<MiniQuiz> getUserMiniQuizzes(String userId);

    //    List<DashboardModel> generateDashboard();
    //
    //    List<NonApprovedModel> nonApproved();
    //
    //    List<UserDashboardResultModel> getUsers();
    //
    //    List<UserResultsModel> getUserResults();
}
