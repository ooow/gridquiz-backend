package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.quiz.QuizService;
import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.Attempt;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import com.griddynamics.gridquiz.rest.model.Request;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizRepository repository;

    @Autowired
    private ResultService resultService;

    @Autowired
    private QuizService quizService;

    @PostMapping(value = "/create")
    @ResponseBody
    public Quiz create(@RequestBody Quiz quiz) {
        return repository.save(quiz);
    }

    @PostMapping(value = "/load")
    @ResponseBody
    public Attempt load(@RequestBody Request<String> request) {
        Result result = resultService.control(request.getUser(), request.getMessage()).orElse(null);
        Quiz quiz = repository.findById(request.getMessage()).orElse(null);

        return Attempt.builder().quiz(quiz).attempt(result).build();
    }

    @PostMapping(value = "/mini")
    @ResponseBody
    public List<MiniQuiz> miniQuizzes(@RequestBody String userId) {
        return quizService.getUserMiniQuizzes(userId);
    }
}
