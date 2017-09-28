package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.api.models.NonApprovedModel;
import com.griddynamics.gridquiz.api.models.UserResultModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.core.services.security.SecurityValidationException;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.jooq.lambda.Seq.seq;

@CrossOrigin
@RestController
@RequestMapping("/api/gridquiz/admin")
public class AdminController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private SecurityValidationService securityValidationService;


    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {

        if (e instanceof SecurityValidationException) {
            return e.getMessage();
        }

        e.printStackTrace();
        return "Internal error.";
    }

    @PostMapping(value = "/quiz/create")
    public String createQuiz(@RequestHeader(value = "X-User-Token") String userToken, @RequestBody Quiz quiz) {
        securityValidationService.isAdmin(userToken);

        quizDao.save(quiz);
        return "Quiz " + quiz.getName() + " created";
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public List<UserResultModel> getUsers(@RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.isAdmin(userToken);

        return quizResultService.getUsers();
    }

    @PostMapping(value = "/users/remove")
    @ResponseBody
    public List<UserResultModel> removeUsers(@RequestHeader(value = "X-User-Token") String userToken, @RequestBody List<Long> userIds) {
        securityValidationService.isAdmin(userToken);

        // remove user results story
        seq(userIds).forEach(userId -> resultDao.removeByUser(userDao.findOne(userId)));
        // delete users
        seq(userIds).forEach(userId -> userDao.delete(userId));

        return quizResultService.getUsers();
    }

    @PostMapping(value = "/non/approved")
    @ResponseBody
    public List<NonApprovedModel> preparingOnApprove(@RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.isAdmin(userToken);

        return quizResultService.nonApproved();
    }

    @PostMapping(value = "/dashboard/approve")
    @ResponseBody
    public List<NonApprovedModel> approve(@RequestHeader(value = "X-User-Token") String userToken, @RequestBody List<Long> resultsIds) {
        securityValidationService.isAdmin(userToken);

        seq(resultsIds).forEach(id -> {
                    UserResult r = resultDao.findOne(id);
                    r.setApproved(true);
                    resultDao.save(r);
                }
        );

        return quizResultService.nonApproved();
    }
}
