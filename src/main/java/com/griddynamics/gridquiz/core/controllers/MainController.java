package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.common.GenerateDateService;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import com.griddynamics.gridquiz.repository.models.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gridquiz")
public class MainController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private GenerateDateService generateDateService;

    @Autowired
    private QuizResultService quizResultService;

    @CrossOrigin
    @PostMapping(value = "/quizzes")
    @ResponseBody
    public Iterable<Quiz> quizzes() {
        return quizDao.findAll();
    }

    @CrossOrigin
    @PostMapping(value = "/quiz/result")
    @ResponseBody
    public Result quizResult(@RequestBody List<UserAnswersModel> answers) {
        return quizResultService.calculateResult(answers);
    }

    @GetMapping(value = "/generate")
    public String generate() {
        generateDateService.generate();
        return "index";
    }
}
