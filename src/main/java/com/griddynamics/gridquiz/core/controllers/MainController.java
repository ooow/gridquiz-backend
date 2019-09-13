package com.griddynamics.gridquiz.core.controllers;

import static java.util.Objects.nonNull;
import static org.jooq.lambda.Seq.seq;

import com.griddynamics.gridquiz.api.models.common.MiniQuizzesModel;
import com.griddynamics.gridquiz.api.models.dashboard.DashboardModel;
import com.griddynamics.gridquiz.api.models.user.UserAnswersModel;
import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.core.services.security.SecurityValidationException;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/gridquiz")
public class MainController {

    @Autowired
    private QuizRepository quizDao;

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

    @PostMapping(value = "/quizzes/history")
    @ResponseBody
    public List<MiniQuizzesModel> loadQuizzesHistoryForUser(
            @RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.validateToken(userToken);

        return seq(quizDao.findAll()).map(q -> {
            MiniQuizzesModel miniQuiz = MiniQuizzesModel.builder()
                    .id(q.getId())
                    .name(q.getName())
                    .description(q.getDescription())
                    .colors(q.getColors())
                    .questionsSize(q.getQuestions().size())
                    .build();

            seq(resultDao.findByQuiz(q)).filter(r -> r.getUser().getToken().equals(userToken))
                    .findFirst()
                    .ifPresent(r -> {
                        miniQuiz.setQuestionsComplete(r.getPoints());
                        if (nonNull(r.getEndTime())) {
                            miniQuiz.setAttempt(true);
                        }
                    });

            return miniQuiz;
        }).toList();
    }

    @PostMapping(value = "/quiz/start")
    @ResponseBody
    public Quiz quiz(@RequestHeader(value = "X-User-Token") String userToken,
                     @RequestBody Long quizId) {
        securityValidationService.canStartQuiz(quizId, userToken);

        quizResultService.startQuiz(quizId, userToken);
        //return quizDao.findOne(quizId);
        return null;
    }

    @PostMapping(value = "/quiz/result")
    @ResponseBody
    public UserResult quizResult(@RequestHeader(value = "X-User-Token") String userToken,
                                 @RequestBody List<UserAnswersModel> answers) {
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
