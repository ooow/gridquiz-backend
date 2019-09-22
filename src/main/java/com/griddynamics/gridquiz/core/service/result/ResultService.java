package com.griddynamics.gridquiz.core.service.result;

import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.AnswersModel.Answer;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import java.util.List;
import java.util.Optional;

public interface ResultService {
    Optional<Result> calculateResult(String userId, Quiz quiz, List<Answer> answers);

    /**
     * Controls the user attempts to open the quiz. When the user opens the quiz for the first time
     * it generates a {@link Result} with start data and stores it in the repository. Next time when
     * the user opens the quiz it returns stored result to allow continue the quiz. If the user has
     * already completed the quiz returns null.
     */
    Optional<Result> progress(String userId, String quizId);

    List<DashboardModel> getDashboardResults(String userId, List<Quiz> quizzes);

    List<DashboardModel> getDashboards(List<Quiz> quizzes);
}
