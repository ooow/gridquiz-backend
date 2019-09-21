package com.griddynamics.gridquiz.core.service.result;

import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserRegistered;
import com.griddynamics.gridquiz.rest.model.DashboardResult;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import com.griddynamics.gridquiz.rest.model.UserAnswers.Answer;
import com.griddynamics.gridquiz.rest.model.UserModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository repository;

    @Override
    public Optional<Result> calculateResult(UserModel user, Quiz quiz, List<Answer> answers) {
        Map<String, String> answersMap = answers.stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Answer::getAnswer));
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(user.getId(), quiz.getId());
        if (result.isPresent()) {
            int points = (int) quiz.getQuestions()
                    .stream()
                    .filter(q -> q.getCorrectAnswer().equals(answersMap.get(q.getId())))
                    .count();
            Result userResult = result.get();
            userResult.setPoints(points);
            userResult.setOutOf(quiz.getQuestions().size());
            userResult.setEndTime(LocalDateTime.now());

            return of(repository.save(userResult));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Result> get(UserModel user, String quizId) {
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
                .approved(false)
                .build();
        return of(repository.save(firstAttempt));
    }

    @Override
    public List<DashboardResult> getDashboardResults(UserRegistered user, List<Quiz> quizzes) {
        return quizzes.stream().map(q -> {
            List<Result> results = repository.findTop5ByQuizIdOrderByPointsDesc(q.getId());
            Optional<Result> currentUserResult =
                    repository.findFirstByUserIdAndQuizId(user.getId(), q.getId());
            MiniQuiz miniQuiz = new MiniQuiz(q);

            return DashboardResult.builder()
                    .miniQuiz(miniQuiz)
                    .top5results(results)
                    .currentUserResult(currentUserResult.orElse(null))
                    .build();
        }).collect(toList());
    }
}
