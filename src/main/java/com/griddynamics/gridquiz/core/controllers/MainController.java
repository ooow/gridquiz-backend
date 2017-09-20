package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.api.models.MiniQuizzesModel;
import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.api.models.UserStartQuizModel;
import com.griddynamics.gridquiz.common.GenerateDateService;
import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.lambda.Seq.seq;

@CrossOrigin
@RestController
@RequestMapping("/api/gridquiz")
public class MainController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private GenerateDateService generateDateService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/quizzes")
    @ResponseBody
    public List<MiniQuizzesModel> quizzes() {

        return seq(quizDao.findAll()).map(quiz -> {
            MiniQuizzesModel miniQuiz = new MiniQuizzesModel();
            miniQuiz.setId(quiz.getId());
            miniQuiz.setName(quiz.getName());
            miniQuiz.setDescription(quiz.getDescription());
            miniQuiz.setColors(quiz.getColors());
            miniQuiz.setQuestionsSize(quiz.getQuestions().size());
            return miniQuiz;
        }).toList();
    }

    @PostMapping(value = "/quiz/start")
    @ResponseBody
    public Quiz quiz(@RequestBody UserStartQuizModel model) {
        //todo validate user and save start to result

        return quizDao.findOne(model.getQuizId());
    }

    @PostMapping(value = "/quiz/create")
    public String createQuiz(@RequestBody Quiz quiz) {
        quizDao.save(quiz);
        String successMessage = "Quiz " + quiz.getName() + " created";

        return successMessage;
    }

    @PostMapping(value = "/quiz/result")
    @ResponseBody
    public UserResult quizResult(@RequestBody List<UserAnswersModel> answers) {
        return quizResultService.calculateResult(answers);
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public Iterable<User> getUsers() {
        return userDao.findAll();
    }

    @PostMapping(value = "/users/remove")
    @ResponseBody
    public Iterable<User> removeUsers(@RequestBody List<Long> userIds) {
        // remove user results story
        seq(userIds).forEach(userId -> resultDao.removeByUser(userDao.findOne(userId)));
        // delete users
        seq(userIds).forEach(userId -> userDao.delete(userId));

        return userDao.findAll();
    }

    @PostMapping(value = "/auth/user")
    @ResponseBody
    public User authUser(@RequestBody User user) {

        return authenticationService.authUser(user);
    }

    /*
    @CrossOrigin
    @PostMapping(value = "/quiz/startquiz")
    @ResponseBody
    public String startQuiz(@RequestBody UserStartQuizModel timestamp) {

    }*/


    @GetMapping(value = "/generate")
    public String generate() {
        generateDateService.generate();
        return "index";
    }
}
