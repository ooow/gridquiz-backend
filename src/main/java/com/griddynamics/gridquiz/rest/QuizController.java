package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import com.griddynamics.gridquiz.rest.model.ProgressModel;
import com.griddynamics.gridquiz.rest.model.Request;
import com.griddynamics.gridquiz.service.quiz.QuizService;
import com.griddynamics.gridquiz.service.result.ResultService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @PostMapping(value = "/progress")
    @ResponseBody
    public ProgressModel progress(@RequestBody Request<String> request) {
        Optional<Result> result = resultService.progress(request.getUserId(), request.getMessage());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("The user already has completed the quiz");
        }

        Optional<Quiz> quiz = repository.findById(request.getMessage());
        if (quiz.isEmpty()) {
            throw new NullPointerException("The quiz is not found");
        }

        return ProgressModel.builder().quiz(quiz.get()).start(result.get().getStartTime()).build();
    }

    @PostMapping(value = "/mini")
    @ResponseBody
    public List<MiniQuizModel> miniQuizzes(@RequestBody String userId) {
        return quizService.getUserMiniQuizzes(userId);
    }
}
