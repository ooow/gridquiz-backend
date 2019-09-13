package com.griddynamics.gridquiz;

import com.griddynamics.gridquiz.repository.AnswerRepository;
import com.griddynamics.gridquiz.repository.QuestionRepository;
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
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public static void main(String[] args) {
        SpringApplication.run(GridQuizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        quizRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
        init();
    }

    private void init() {
        List<Answer> answersOne = List.of(Answer.builder().text("Culture").correctly(true).build(),
                                          Answer.builder().text("Role").correctly(false).build(),
                                          Answer.builder().text("Team").correctly(false).build(),
                                          Answer.builder()
                                                  .text("Application")
                                                  .correctly(false)
                                                  .build());
        List<Answer> answersTwo =
                List.of(Answer.builder().text("docker list").correctly(false).build(),
                        Answer.builder().text("docker containers list").correctly(false).build(),
                        Answer.builder().text("docker ps").correctly(true).build(),
                        Answer.builder().text("docker list cs").correctly(false).build());

        List<Question> questions = List.of(Question.builder()
                                                   .title("DevOps is")
                                                   .type(Question.Type.TEXT)
                                                   .answers(answersOne)
                                                   .build(), Question.builder()
                                                   .title("What is the command in Docker to list all running containers?")
                                                   .type(Question.Type.TEXT)
                                                   .answers(answersTwo)
                                                   .build());

        List<Color> colorList = new ArrayList<>();

        Color color1 = new Color();
        color1.setCode("#B66D18");
        colorList.add(color1);

        Color color2 = new Color();
        color2.setCode("#984E03");
        colorList.add(color2);

        answerRepository.saveAll(answersOne);
        answerRepository.saveAll(answersTwo);
        questionRepository.saveAll(questions);
        quizRepository.save(Quiz.builder()
                                    .name("DevOps")
                                    .description("DevOps Tech Quiz")
                                    .type(Quiz.Type.TEST)
                                    .questions(questions)
                                    .colors(colorList)
                                    .build());

        System.out.println("Generate Data Service: DevOps Quiz Generated.");
    }
}
