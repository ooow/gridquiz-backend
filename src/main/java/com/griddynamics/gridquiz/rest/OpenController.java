package com.griddynamics.gridquiz.rest;

import static org.jooq.lambda.Seq.seq;

import com.griddynamics.gridquiz.api.models.common.MiniQuizzesModel;
import com.griddynamics.gridquiz.repository.QuizRepository;
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
    private QuizRepository quizRepository;

    @GetMapping(value = "/quizzes")
    @ResponseBody
    public List<MiniQuizzesModel> quizzes() {
        return seq(quizRepository.findAll()).map(q -> MiniQuizzesModel.builder()
                .id(q.getId())
                .name(q.getName())
                .description(q.getDescription())
                .colors(q.getColors())
                .questionsSize(q.getQuestions().size())
                .questionsComplete(0)
                .build()).toList();
    }
}
