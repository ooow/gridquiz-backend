package com.griddynamics.gridquiz.core.service.result;

import static java.util.Objects.nonNull;

import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserInternal;
import com.griddynamics.gridquiz.rest.model.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository repository;

    @Override
    public Optional<Result> calculateResult(UserInternal user,
                                            Quiz quiz,
                                            Map<String, String> answers) {
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(user.getId(), quiz.getId());
        if (result.isPresent()) {
            long points = quiz.getQuestions()
                    .stream()
                    .filter(q -> q.getCorrectAnswer().equals(answers.get(q.getId())))
                    .count();
            return Optional.of(repository.save(Result.builder()
                                                       .userId(user.getId())
                                                       .quizId(quiz.getId())
                                                       .startTime(result.get().getStartTime())
                                                       .endTime(LocalDateTime.now())
                                                       .points(points)
                                                       .approved(false)
                                                       .build()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Result> control(User user, String quizId) {
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(user.getId(), quizId);

        if (result.isPresent()) {
            if (nonNull(result.get().getEndTime())) {
                // The user has already completed the quiz.
                return Optional.empty();
            }
            // The user still in progress.
            return result;
        }

        // The user just starts the quiz.
        Result firstAttempt = Result.builder()
                .userId(user.getId())
                .quizId(quizId)
                .startTime(LocalDateTime.now())
                .points(0)
                .approved(false)
                .build();
        return Optional.of(repository.save(firstAttempt));
    }
}
