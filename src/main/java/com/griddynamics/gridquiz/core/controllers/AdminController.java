package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.api.models.common.NonApprovedModel;
import com.griddynamics.gridquiz.api.models.user.UserDashboardResultModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.core.services.ReportService;
import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.core.services.common.FileDownload;
import com.griddynamics.gridquiz.core.services.security.SecurityValidationException;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.models.Quiz;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/gridquiz/admin")
public class AdminController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private SecurityValidationService securityValidationService;

    @Autowired
    private ReportService reportService;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {

        if (e instanceof SecurityValidationException) {
            return e.getMessage();
        }

        e.printStackTrace();
        return "Internal error.";
    }

    @PostMapping(value = "/quiz/create")
    public String createQuiz(@RequestHeader(value = "X-User-Token") String userToken,
                             @RequestBody Quiz quiz) {
        securityValidationService.isAdmin(userToken);

        quizRepository.save(quiz);
        return String.format("Quiz %s created", quiz.getName());
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public List<UserDashboardResultModel> getUsers(
            @RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.isAdmin(userToken);

        return quizResultService.getUsers();
    }

    @PostMapping(value = "/users/remove")
    @ResponseBody
    public List<UserDashboardResultModel> removeUsers(
            @RequestHeader(value = "X-User-Token") String userToken,
            @RequestBody List<Long> userIds) {
        securityValidationService.isAdmin(userToken);

        //        // remove user results story
        //        seq(userIds).forEach(userId -> resultRepository.removeByUser(userRepository.findOne(userId)));
        //        // delete users
        //        seq(userIds).forEach(userId -> userRepository.delete(userId));

        return quizResultService.getUsers();
    }

    @PostMapping(value = "/non/approved")
    @ResponseBody
    public List<NonApprovedModel> preparingOnApprove(
            @RequestHeader(value = "X-User-Token") String userToken) {
        securityValidationService.isAdmin(userToken);

        return quizResultService.nonApproved();
    }

    @PostMapping(value = "/dashboard/approve")
    @ResponseBody
    public List<NonApprovedModel> approve(@RequestHeader(value = "X-User-Token") String userToken,
                                          @RequestBody List<Long> resultsIds) {
        securityValidationService.isAdmin(userToken);
        //
        //        seq(resultsIds).forEach(id -> {
        //                    UserResult r = resultRepository.findOne(id);
        //                    r.setApproved(true);
        //                    resultRepository.save(r);
        //                }
        //        );

        return quizResultService.nonApproved();
    }

    @GetMapping(value = "/download/report")
    public void downloadReport(@RequestHeader(value = "X-User-Token") String userToken,
                               HttpServletResponse response) throws IOException {
        securityValidationService.isAdmin(userToken);

        FileDownload.newFileDownload()
                .withData(reportService.generateReport())
                .withFileName(String.format("GridQuiz Report %s.xlsx", LocalDate.now()))
                .applyToResponse(response)
                .withXlsxContentType()
                .send();
    }
}
