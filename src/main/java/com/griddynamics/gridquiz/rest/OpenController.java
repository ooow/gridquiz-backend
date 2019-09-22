package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.quiz.QuizService;
import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.rest.model.DashboardModel;
import com.griddynamics.gridquiz.rest.model.MiniQuizModel;
import java.util.List;
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
}
