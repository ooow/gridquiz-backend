package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
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

    @PostMapping(value = "/create")
    @ResponseBody
    public Quiz create(@RequestBody Quiz quiz) {
        return repository.save(quiz);
    }

    @PostMapping(value = "/start")
    @ResponseBody
    public Quiz start(@RequestBody User user, @RequestBody String quizId) {
        resultService.startQuiz(user, quizId);
        return repository.findById(quizId).orElse(null);
    }

    @PostMapping(value = "/history")
    @ResponseBody
    public List<MiniQuiz> history(@RequestBody User user, @RequestBody String quizId) {
        // TODO: Implement.
        return null;
    }
}
