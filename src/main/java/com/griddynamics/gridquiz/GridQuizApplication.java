package com.griddynamics.gridquiz;

import com.griddynamics.gridquiz.repository.QuestionRepository;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Question;
import com.griddynamics.gridquiz.repository.model.Quiz;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GridQuizApplication implements CommandLineRunner {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    public static void main(String[] args) {
        SpringApplication.run(GridQuizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        quizRepository.deleteAll();
        userRepository.deleteAll();
        questionRepository.deleteAll();
        resultRepository.deleteAll();
        init();
    }

    private void init() {
        Question one = Question.builder()
                .title("DevOps is")
                .answers(List.of("Culture", "Role", "Team", "Application"))
                .correctAnswer("Culture")
                .build();

        Question two = Question.builder()
                .title("What is the command in Docker to list all running containers?")
                .answers(List.of("docker list", "docker containers list", "docker ps",
                                 "docker list cs"))
                .correctAnswer("docker ps")
                .build();
        List<Question> questions = List.of(one, two);

        questionRepository.saveAll(questions);
        quizRepository.save(Quiz.builder()
                                    .name("DevOps")
                                    .description("DevOps Tech Quiz")
                                    .questions(questions).colors(List.of("#B66D18", "#984E03"))
                                    .build());

        System.out.println("Generate Data Service: DevOps Quiz Generated.");
    }
}
