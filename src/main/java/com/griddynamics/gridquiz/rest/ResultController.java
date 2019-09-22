package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.UserRegistered;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import com.griddynamics.gridquiz.rest.model.Request;
import com.griddynamics.gridquiz.rest.model.UserAnswers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @PostMapping(value = "/submit")
    @ResponseBody
    public Result submit(@RequestBody Request<UserAnswers> userAnswers) {
        Optional<Quiz> quiz = quizRepository.findById(userAnswers.getMessage().getQuizId());

        if (quiz.isPresent()) {
            return service.calculateResult(userAnswers.getUserId(), quiz.get(),
                                           userAnswers.getMessage().getAnswers()).orElse(null);
        }
        return null; // TODO: handel this case.
    }

    @PostMapping(value = "/dashboards")
    @ResponseBody
    public List<DashboardModel> dashboard(@RequestBody String userId) {
        Optional<UserRegistered> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Quiz> quizzes = quizRepository.findAll();
            return service.getDashboardResults(user.get(), quizzes);
        }
        return null; // TODO: handel this case.
    }
}
