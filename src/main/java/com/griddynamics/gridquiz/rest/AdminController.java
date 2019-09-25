package com.griddynamics.gridquiz.rest;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.USER;
import static java.util.stream.Collectors.toList;

import com.griddynamics.gridquiz.core.service.report.ReportService;
import com.griddynamics.gridquiz.repository.QuestionRepository;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.rest.model.QuizModel;
import com.griddynamics.gridquiz.rest.model.UserModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportService reportService;

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @GetMapping(value = "/check")
    public ResponseEntity check() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/quiz/save")
    @ResponseBody
    public Quiz save(@RequestBody QuizModel quizModel) {
        Quiz quiz = quizModel.toObject();
        quiz.getQuestions().forEach(questionRepository::save);
        return quizRepository.save(quiz);
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public List<UserModel> users() {
        return userRepository.findAll()
                .stream()
                .map(UserModel::new)
                .filter(u -> u.getRole().equals(USER))
                .collect(toList());
    }

    @GetMapping(value = "/download/report")
    public void downloadReport(HttpServletResponse response) throws IOException {
        String fileName = String.format("GridQuiz Report %s.xlsx", LocalDate.now());
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                           String.format("attachment;filename=%s", fileName));
        InputStream data = new ByteArrayInputStream(reportService.generateReport());
        IOUtils.copy(data, response.getOutputStream());
    }
}
