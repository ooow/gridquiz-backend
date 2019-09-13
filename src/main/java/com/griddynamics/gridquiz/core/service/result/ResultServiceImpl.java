package com.griddynamics.gridquiz.core.service.result;

import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.User;
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
    public Result calculateResult(User user, Quiz quiz, Map<String, String> answers) {
        Optional<Result> result = repository.findFirstByUserIdAndQuizId(user.getId(), quiz.getId());
        if (result.isPresent()) {
            long points = quiz.getQuestions()
                    .stream()
                    .filter(q -> q.getCorrectAnswer().equals(answers.get(q.getId())))
                    .count();
            return Result.builder()
                    .userId(user.getId())
                    .quizId(quiz.getId())
                    .startTime(result.get().getStartTime())
                    .endTime(LocalDateTime.now())
                    .points(points)
                    .approved(false)
                    .build();
        }
        return null;
    }

    @Override
    public Result startQuiz(User user, String quizId) {
        return Result.builder()
                .userId(user.getId())
                .quizId(quizId)
                .startTime(LocalDateTime.now())
                .points(0)
                .approved(false)
                .build();
    }
}
