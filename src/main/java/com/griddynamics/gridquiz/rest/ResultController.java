package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.model.AnswersModel;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import com.griddynamics.gridquiz.rest.model.Request;
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
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private ResultService service;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public Result submit(@RequestBody Request<AnswersModel> userAnswers) {
        Optional<Quiz> quiz = quizRepository.findById(userAnswers.getMessage().getQuizId());
        if (quiz.isEmpty()) {
            throw new NullPointerException("The quiz is not found");
        }

        Optional<Result> result = service.calculateResult(userAnswers.getUserId(), quiz.get(),
                                                          userAnswers.getMessage().getAnswers());
        if (result.isEmpty()) {
            throw new NullPointerException("The user has not started the quiz");
        }
        return result.get();
    }

    @PostMapping(value = "/dashboards")
    @ResponseBody
    public List<DashboardModel> dashboard(@RequestBody String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user is not found");
        }

        List<Quiz> quizzes = quizRepository.findAll();
        return service.getDashboardResults(user.get().getId(), quizzes);
    }
}
