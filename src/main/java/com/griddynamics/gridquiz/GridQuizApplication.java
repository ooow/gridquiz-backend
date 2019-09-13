package com.griddynamics.gridquiz;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.models.Answer;
import com.griddynamics.gridquiz.repository.models.Color;
import com.griddynamics.gridquiz.repository.models.Question;
import com.griddynamics.gridquiz.repository.models.Quiz;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GridQuizApplication implements CommandLineRunner {

    @Autowired
    private QuizRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(GridQuizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();
        init();
    }

    private void init() {
        Question question = Question.builder()
                .title("DevOps is")
                .type(Question.Type.TEXT)
                .answers(List.of(Answer.builder().text("Culture").correctly(true).build(),
                                 Answer.builder().text("Role").correctly(false).build(),
                                 Answer.builder().text("Team").correctly(false).build(),
                                 Answer.builder().text("Application").correctly(false).build()))
                .build();

        Question questionT = Question.builder()
                .title("What is the command in Docker to list all running containers?")
                .type(Question.Type.TEXT)
                .answers(List.of(Answer.builder().text("docker list").correctly(false).build(),
                                 Answer.builder()
                                         .text("docker containers list")
                                         .correctly(false)
                                         .build(),
                                 Answer.builder().text("docker ps").correctly(true).build(),
                                 Answer.builder().text("docker list cs").correctly(false).build()))
                .build();

        List<Color> colorList = new ArrayList<>();

        Color color1 = new Color();
        color1.setCode("#B66D18");
        colorList.add(color1);

        Color color2 = new Color();
        color2.setCode("#984E03");
        colorList.add(color2);

        repository.save(Quiz.builder()
                                .name("DevOps")
                                .description("DevOps Tech Quiz")
                                .type(Quiz.Type.TEST)
                                .questions(List.of(question, questionT))
                                .colors(colorList)
                                .build());

        System.out.println("Generate Data Service: DevOps Quiz Generated.");
    }
}
