package com.griddynamics.gridquiz;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.ADMIN;
import static com.griddynamics.gridquiz.repository.model.Role.Enum.USER;

import com.griddynamics.gridquiz.repository.QuestionRepository;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.RoleRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Question;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Role;
import com.griddynamics.gridquiz.repository.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(GridQuizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //clean();
        //init();
    }

    private void clean() {
        roleRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();
        questionRepository.deleteAll();
        resultRepository.deleteAll();
    }

    private void init() {
        initRoles();
        initAdmin();
        initGeneralQuiz();
        initDevOpsQuiz();
        initJavaQuiz();
    }

    private void initRoles() {
        if (roleRepository.findAll().size() == 0) {
            roleRepository.save(
                    roleRepository.findByRole(USER).orElse(Role.builder().role(USER).build()));
            roleRepository.save(
                    roleRepository.findByRole(ADMIN).orElse(Role.builder().role(ADMIN).build()));
            System.out.println("Generate Data Service: Roles Generated.");
        }
    }

    private void initAdmin() {
        Optional<User> admin = userRepository.findByEmail("admin@admin.com");
        if (admin.isEmpty()) {
            userRepository.save(User.builder().email("admin@admin.com")
                                        .name("admin")
                                        .roles(Set.of(roleRepository.findByRole(USER).get(),
                                                      roleRepository.findByRole(ADMIN).get()))
                                        .build());
            System.out.println("Generate Data Service: Admin Generated.");
        }
    }

    private void initDevOpsQuiz() {
        Question one = Question.builder().text("DevOps is")
                .answers(List.of("Culture", "Role", "Team", "Application"))
                .correctAnswer("Culture")
                .build();

        Question two = Question.builder()
                .text("What is the command in Docker to list all running containers?")
                .answers(List.of("docker list", "docker containers list", "docker ps",
                                 "docker list cs"))
                .correctAnswer("docker ps")
                .build();
        List<Question> questions = List.of(one, two);

        questionRepository.saveAll(questions);
        quizRepository.save(Quiz.builder()
                                    .name("DevOps")
                                    .description("DevOps Tech")
                                    .questions(questions)
                                    .color("#FF8B00")
                                    .build());

        System.out.println("Generate Data Service: DevOps Quiz Generated.");
    }

    private void initJavaQuiz() {
        Question one = Question.builder()
                .text("What is the result ?")
                .code("public class Vitamins {\n" + "\tpublic static void main(String[] args) {\n"
                              + "\t\tList<String> vitamins = new ArrayList<>();\n"
                              + "\t\tvitamins.add(\"A\");\n" + "\t\tvitamins.add(\"B12\");\n"
                              + "\t\tvitamins.add(\"C\");\n" + "\t\tvitamins.set(1, \"B\");\n"
                              + "\t\tvitamins.add(1, \"D\");\n"
                              + "\t\tSystem.out.println(vitamins);\n" + "\t}\n" + "}\n")
                .answers(List.of("[A, D, B, C]", "[D, B, C]", "[A, D, C]", "[D, B12, C]"))
                .correctAnswer("[A, D, B, C]")
                .build();

        Question two = Question.builder()
                .text("Which statement is true about this code ?")
                .code("class Engine {\n" + "}\n" + " \n" + "public class App {\n"
                              + "\tpublic static void main(String[] args) {\n"
                              + "\t\tEngine e = new Engine();\n" + "\t\tEngine e1 = e;\n"
                              + "\t\te = null;\n" + "\t}\n" + "}\n")
                .answers(
                        List.of("It creates an object and object is not eligible for garbage collection",
                                "It creates an object and object is eligible for garbage collection",
                                "It creates two objects and both objects are not eligible for garbage collection",
                                "It creates two objects: e and e1. The e object is eligible for garbage collection"))
                .correctAnswer("It creates an object and object is eligible for garbage collection")
                .build();
        List<Question> questions = List.of(one, two);

        questionRepository.saveAll(questions);
        quizRepository.save(Quiz.builder()
                                    .name("Java")
                                    .description("Java Experience")
                                    .questions(questions)
                                    .color("#006BB9")
                                    .build());

        System.out.println("Generate Data Service: Java Quiz Generated.");
    }

    private void initGeneralQuiz() {
        Question one = Question.builder()
                .text("Grid Dynamics is an Exclusive Partner for Lviv IT Arena. Select our technology")
                .answers(List.of("Gamedev", "Cloud", "ProductDev"))
                .correctAnswer("Cloud")
                .build();

        Question two = Question.builder()
                .text("Stop by our booth and attend Live Demo session. Complete the secret phrase provided by a speaker at the end of the session: \"Artificial Intelligence is the ...\"")
                .answers(List.of("Past", "Future", "Problem"))
                .correctAnswer("Future")
                .build();
        List<Question> questions = List.of(one, two);

        questionRepository.saveAll(questions);
        quizRepository.save(Quiz.builder()
                                    .name("General")
                                    .description("General Quiz")
                                    .questions(questions)
                                    .color("#A9AA00")
                                    .build());

        System.out.println("Generate Data Service: General Quiz Generated.");
    }
}
