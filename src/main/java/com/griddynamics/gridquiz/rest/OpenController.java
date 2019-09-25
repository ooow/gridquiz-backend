package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.quiz.QuizService;
import com.griddynamics.gridquiz.core.service.report.ReportService;
import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/open")
public class OpenController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/mini")
    @ResponseBody
    public List<MiniQuizModel> quizzes() {
        return quizService.getAllMiniQuizzes();
    }

    @GetMapping(value = "/dashboards")
    @ResponseBody
    public List<DashboardModel> dashboards() {
        return resultService.getDashboards(quizRepository.findAll());
    }

    @GetMapping(value = "/download/reporttt")
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
