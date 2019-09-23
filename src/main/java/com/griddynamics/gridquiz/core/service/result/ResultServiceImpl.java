package com.griddynamics.gridquiz.core.service.result;

import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.AnswersModel.Answer;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import com.griddynamics.gridquiz.rest.model.ResultModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Optional<Result> calculateResult(String userId, Quiz quiz, List<Answer> answers) {
        Map<String, String> answersMap = answers.stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Answer::getAnswer));
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(userId, quiz.getId());
        if (result.isPresent()) {
            int points = (int) quiz.getQuestions()
                    .stream()
                    .filter(q -> q.getCorrectAnswer().equals(answersMap.get(q.getId())))
                    .count();
            Result userResult = result.get();
            userResult.setPoints(points);
            userResult.setOutOf(quiz.getQuestions().size());
            userResult.setEndTime(LocalDateTime.now());
            userResult.setSeconds(
                    getSecondsBetween(userResult.getStartTime(), userResult.getEndTime()));

            return of(repository.save(userResult));
        }
        return empty();
    }

    @Override
    public Optional<Result> progress(String userId, String quizId) {
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(userId, quizId);

        if (result.isPresent()) {
            if (nonNull(result.get().getEndTime())) {
                // The user has already completed the quiz.
                return empty();
            }
            // The user still in progress.
            return result;
        }

        // The user just starts the quiz.
        Result firstAttempt = Result.builder().userId(userId)
                .quizId(quizId)
                .startTime(LocalDateTime.now())
                .build();
        return of(repository.save(firstAttempt));
    }

    @Override
    public List<DashboardModel> getDashboardResults(String userId, List<Quiz> quizzes) {
        return quizzes.stream().map(q -> {
            List<Result> results = sortResultsByPointsAndTime(
                    repository.findTop5ByQuizIdOrderByPointsDesc(q.getId()));
            MiniQuizModel miniQuiz = new MiniQuizModel(q);

            List<ResultModel> resultModels = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                Result res = results.get(i);
                ResultModel model = new ResultModel(res);
                model.setHighlighted(res.getUserId().equals(userId));
                model.setPlace(i + 1); // Starts from 1.
                resultModels.add(model);
            }

            if (resultModels.stream().noneMatch(r -> userId.equals(r.getUserId()))) {
                getUserResult(userId, q.getId()).ifPresent(resultModels::add);
            }

            return DashboardModel.builder().miniQuiz(miniQuiz).results(resultModels).build();
        }).collect(toList());
    }

    @Override
    public List<DashboardModel> getDashboards(List<Quiz> quizzes) {
        return quizzes.stream().map(q -> {
            List<Result> results = sortResultsByPointsAndTime(
                    repository.findTop5ByQuizIdOrderByPointsDesc(q.getId()));
            MiniQuizModel miniQuiz = new MiniQuizModel(q);

            List<ResultModel> resultModels = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                Result res = results.get(i);
                ResultModel model = new ResultModel(res);
                model.setPlace(i + 1); // Starts from 1.
                resultModels.add(model);
            }

            return DashboardModel.builder().miniQuiz(miniQuiz).results(resultModels).build();
        }).collect(toList());
    }

    private Optional<ResultModel> getUserResult(String userId, String quizId) {
        List<Result> allQuizResults = repository.findAllByQuizId(quizId);

        for (int i = 0; i < allQuizResults.size(); i++) {
            if (userId.equals(allQuizResults.get(i).getUserId())) {
                // User's result in all quiz results.
                ResultModel model = new ResultModel(allQuizResults.get(i));
                model.setHighlighted(true);
                model.setPlace(i + 1); // Starts from 1.
                return of(model);
            }
        }

        // User has not result of this quiz.
        return empty();
    }

    private List<Result> sortResultsByPointsAndTime(List<Result> results) {
        results.sort((a, b) -> {
            if (a.getPoints() != b.getPoints()) {
                return Integer.compare(a.getPoints(), b.getPoints()) * -1; // Desc
            } else {
                return Long.compare(b.getSeconds(), a.getSeconds()) * -1; // Desc
            }
        });
        return results;
    }

    private long getSecondsBetween(LocalDateTime start, LocalDateTime end) {
        return Math.abs(Duration.between(start, end).getSeconds());
    }
}
