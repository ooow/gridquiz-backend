package com.griddynamics.gridquiz.core.controllers;

import com.griddynamics.gridquiz.rest.model.MiniQuiz;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gridquiz")
public class MainController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return "Internal error.";
    }

    @PostMapping(value = "/quizzes/history")
    @ResponseBody
    public List<MiniQuiz> loadQuizzesHistoryForUser(
            @RequestHeader(value = "X-User-Token") String userToken) {

        return new ArrayList<>();
        //        return seq(quizRepository.findAll()).map(q -> {
        //            MiniQuiz miniQuiz = MiniQuiz.builder()
        //                    .id(q.getId())
        //                    .name(q.getName())
        //                    .description(q.getDescription())
        //                    .colors(q.getColors())
        //                    .questionsSize(q.getQuestions().size())
        //                    .build();
        //
        //            seq(resultRepository.findByQuiz(q)).filter(
        //                    r -> r.getUser().getToken().equals(userToken)).findFirst().ifPresent(r -> {
        //                miniQuiz.setQuestionsComplete(r.getPoints());
        //                if (nonNull(r.getEndTime())) {
        //                    miniQuiz.setAttempt(true);
        //                }
        //            });
        //
        //            return miniQuiz;
        //        }).toList();
    }
}
