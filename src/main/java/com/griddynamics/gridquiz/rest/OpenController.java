package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.api.models.dashboard.DashboardModel;
import com.griddynamics.gridquiz.core.service.quiz.QuizService;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.model.MiniQuiz;
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
    private UserRepository userRepository;

    @GetMapping(value = "/mini/quizzes")
    @ResponseBody
    public List<MiniQuiz> quizzes() {
        return quizService.getAllMiniQuizzes();
    }

    @GetMapping(value = "/dashboard")
    @ResponseBody
    public List<DashboardModel> dashboard() {
        return null; //quizResultService.generateDashboard();
    }

    @GetMapping(value = "/user/load")
    @ResponseBody
    public User getUser() {
        return userRepository.findAll().get(0); //TODO: Temp.
    }
}
