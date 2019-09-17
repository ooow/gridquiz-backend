package com.griddynamics.gridquiz.core.service.result;

import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserInternal;
import com.griddynamics.gridquiz.rest.model.User;
import java.util.Map;
import java.util.Optional;

public interface ResultService {
    Optional<Result> calculateResult(UserInternal user, Quiz quiz, Map<String, String> answers);

    /**
     * Controls the user attempts to open the quiz. When the user opens the quiz for the first time
     * it generates a {@link Result} with start data and stores it in the repository. Next time when
     * the user opens the quiz it returns stored result to allow continue the quiz. If the user has
     * already completed the quiz returns null.
     */
    Optional<Result> control(User user, String quizId);
}
