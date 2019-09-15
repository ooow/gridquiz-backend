package com.griddynamics.gridquiz.core.service.quiz;

import static java.util.Optional.ofNullable;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<MiniQuiz> getAllMiniQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(q -> MiniQuiz.builder()
                        .id(q.getId())
                        .name(q.getName())
                        .description(q.getDescription())
                        .colors(q.getColors())
                        .questionsSize(q.getQuestions().size())
                        .questionsComplete(0)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<MiniQuiz> getUserMiniQuizzes(String userId) {
        Map<String, Long> results = resultRepository.findByUserId(userId)
                .stream()
                .collect(Collectors.toMap(Result::getQuizId, Result::getPoints));
        return quizRepository.findAll()
                .stream()
                .map(q -> MiniQuiz.builder()
                        .id(q.getId())
                        .name(q.getName())
                        .description(q.getDescription())
                        .colors(q.getColors())
                        .questionsSize(q.getQuestions().size())
                        .questionsComplete(ofNullable(results.get(q.getId())).orElse(0L))
                        .build())
                .collect(Collectors.toList());
    }
}
