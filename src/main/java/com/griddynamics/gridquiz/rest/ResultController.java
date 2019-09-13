package com.griddynamics.gridquiz.rest;

import com.griddynamics.gridquiz.core.service.result.ResultService;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private ResultRepository repository;

    @Autowired
    private ResultService service;

    @PostMapping(value = "/submit")
    @ResponseBody
    public Result submit(@RequestBody User user,
                         @RequestBody Quiz quiz,
                         @RequestBody Map<String, String> answers) {

        return repository.save(service.calculateResult(user, quiz, answers));
    }
}
