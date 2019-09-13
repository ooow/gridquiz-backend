package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizRepository repository;

    @PostMapping(value = "/create")
    @ResponseBody
    public Quiz create(@RequestBody Quiz quiz) {
        return repository.save(quiz);
    }

    @PostMapping(value = "/start")
    @ResponseBody
    public Quiz quiz(@RequestHeader(value = "X-User-Token") String userToken,
                     @RequestBody String quizId) {
        //quizResultService.startQuiz(quizId, userToken);
        //return quizDao.findOne(quizId);
        return repository.findById(quizId).orElse(null);
    }
}
