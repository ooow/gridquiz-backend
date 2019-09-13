package com.griddynamics.gridquiz.core.service.quiz;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository repository;

    @Override
    public List<MiniQuiz> getAllMiniQuizzes() {
        return repository.findAll()
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
}
