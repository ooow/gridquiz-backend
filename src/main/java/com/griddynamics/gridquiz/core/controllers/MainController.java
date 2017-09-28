package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.api.models.DashboardModel;
import com.griddynamics.gridquiz.api.models.MiniQuizzesModel;
import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.core.services.security.SecurityValidationException;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.jooq.lambda.Seq.seq;

@CrossOrigin
@RestController
@RequestMapping("/api/gridquiz")
public class MainController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SecurityValidationService securityValidationService;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {

        if (e instanceof SecurityValidationException) {
            e.printStackTrace();
            return e.getMessage();
        }

        e.printStackTrace();
        return "Internal error.";
    }

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
            miniQuiz.setQuestionsComplete(0);
            return miniQuiz;
        }).toList();
    }

    @PostMapping(value = "/quizzes/history")
    @ResponseBody
    public List<MiniQuizzesModel> loadQuizzesHistoryForUser(@RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.validateToken(userToken);

        return seq(quizDao.findAll()).map(quiz -> {
            MiniQuizzesModel miniQuiz = new MiniQuizzesModel();
            miniQuiz.setId(quiz.getId());
            miniQuiz.setName(quiz.getName());
            miniQuiz.setDescription(quiz.getDescription());
            miniQuiz.setColors(quiz.getColors());
            miniQuiz.setQuestionsSize(quiz.getQuestions().size());

            seq(resultDao.findByQuiz(quiz))
                    .filter(r -> r.getUser().getToken().equals(userToken))
                    .findFirst()
                    .ifPresent(r -> {
                        miniQuiz.setQuestionsComplete(r.getPoints());
                        if (Objects.nonNull(r.getEndTime())) {
                            miniQuiz.setAttempt(true);
                        }
                    });

            return miniQuiz;
        }).toList();
    }

    @PostMapping(value = "/quiz/start")
    @ResponseBody
    public Quiz quiz(@RequestHeader(value = "X-User-Token") String userToken, @RequestBody Long quizId) {
        securityValidationService.canStartQuiz(quizId, userToken);

        quizResultService.startQuiz(quizId, userToken);
        return quizDao.findOne(quizId);
    }

    @PostMapping(value = "/quiz/result")
    @ResponseBody
    public UserResult quizResult(@RequestHeader(value = "X-User-Token") String userToken, @RequestBody List<UserAnswersModel> answers) {
        securityValidationService.validateToken(userToken);

        return quizResultService.calculateResult(answers, userToken);
    }

    @PostMapping(value = "/auth/user")
    @ResponseBody
    public User authUser(@RequestBody User user) {

        return authenticationService.authUser(user);
    }

    @GetMapping(value = "/dashboard")
    public List<DashboardModel> dashboard() {
        return quizResultService.generateDashboard();
    }
}
