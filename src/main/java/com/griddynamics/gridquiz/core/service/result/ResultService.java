package com.griddynamics.gridquiz.core.service.result;

import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.User;
import java.util.Map;

public interface ResultService {
    Result calculateResult(User user, Quiz quiz, Map<String, String> answers);

    Result startQuiz(User user, String quizId);
}
