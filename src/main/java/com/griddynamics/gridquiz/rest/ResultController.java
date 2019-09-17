package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.rest.model.Request;
import com.griddynamics.gridquiz.rest.model.UserAnswers;
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

    @PostMapping(value = "/submit")
    @ResponseBody
    public Result submit(@RequestBody Request<UserAnswers> userAnswers) {
        Optional<Quiz> quiz = quizRepository.findById(userAnswers.getMessage().getQuizId());

        if (quiz.isPresent()) {
            return service.calculateResult(userAnswers.getUser(), quiz.get(),
                                           userAnswers.getMessage().getAnswers()).orElse(null);
        }
        return null; // TODO: handel this case.
    }
}
