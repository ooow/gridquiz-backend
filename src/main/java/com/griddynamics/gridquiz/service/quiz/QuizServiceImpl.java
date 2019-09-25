package com.griddynamics.gridquiz.service.quiz;

import static java.util.Objects.nonNull;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<MiniQuizModel> getAllMiniQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(MiniQuizModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MiniQuizModel> getUserMiniQuizzes(String userId) {
        List<Result> userResults = resultRepository.findAllByUserId(userId);
        Map<String, Integer> results = userResults.stream()
                .collect(Collectors.toMap(Result::getQuizId, Result::getPoints));
        return quizRepository.findAll().stream().map(q -> {
            MiniQuizModel mini = new MiniQuizModel(q);
            if (nonNull(results.get(q.getId()))) {
                mini.setQuestionsComplete(results.get(q.getId()));
                Optional<Result> result =
                        resultRepository.findFirstByUserIdAndQuizId(userId, q.getId());
                // TODO: finished instead of attempt.
                result.ifPresent(r -> mini.setAttempt(nonNull(r.getEndTime())));
            }
            return mini;
        }).collect(Collectors.toList());
    }
}
