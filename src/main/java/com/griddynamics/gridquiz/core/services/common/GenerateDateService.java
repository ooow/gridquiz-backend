package com.griddynamics.gridquiz.core.services.common;

import com.griddynamics.gridquiz.core.services.security.TokenGenerator;
import com.griddynamics.gridquiz.repository.AnswerRepository;
import com.griddynamics.gridquiz.repository.ColorRepository;
import com.griddynamics.gridquiz.repository.QuestionRepository;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.QuizResultMessageRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.models.Answer;
import com.griddynamics.gridquiz.repository.models.Color;
import com.griddynamics.gridquiz.repository.models.Question;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateDateService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private QuizResultMessageRepository messageRepository;

    public void initAdmin() {
        User admin = userRepository.findByEmail("admin@hr.com");
        if (Objects.isNull(admin)) {
            admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@hr.com");
            admin.setRole(Role.ADMIN);
            admin.setToken(TokenGenerator.generateToken("admin"));
            userRepository.save(admin);
        }
    }

    public void generate() {
        if (Objects.isNull(quizRepository.findByName("DevOps"))) {
            //DevOps Technical Quiz
            generateDevOpsQuiz();
        }

        if (Objects.isNull(quizRepository.findByName("Java"))) {
            // Java Tech Quiz
            generateJavaQuiz();
        }

        if (Objects.isNull(quizRepository.findByName("General"))) {
            // General Quiz
            generateGeneralQuiz();
        }
    }

    private void generateDevOpsQuiz() {
        Answer a;
        Question q;

        ArrayList<Answer> answers = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();

        a = new Answer();
        a.setText("Culture");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Role");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Team");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Application");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("DevOps is");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("docker list");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("docker containers list");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("docker ps");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("docker list cs");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("What is the command in Docker to list all running containers ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("HCL");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("JSON");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("YAML");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Playbooks in Ansible are in");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Chef");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Salt");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Pepper");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Which one of followings is NOT a CMT (Configuration Management Tool)");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("True");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("False");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Cloudformation Templates are simple JSON-formatted text files");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("ENTRYPOINT and FROM");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("FROM and MAINTAINER");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("USER and FROM");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Name two commands that cannot be triggered by the ONBUILD docker command");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("DevOps looks to extend Dev into Production");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("DevOps looks to embed Development into IT Operations");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("DevOps looks to embed IT Operations into Development");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("All of the above");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Which of these statements are true");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Debian");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Ubuntu");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Red Hat");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Gentoo");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Arch");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Yum package manager is used by which of the Linux distributions");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("True");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("False");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "[AWS] Does ELB ( Elastic Load Balancer ) support EIP (Elastic IP) Association - True or False ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("On-Demand Instances");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Spot Instances");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Reserved Instances");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "[AWS] What’s the preferred instance type for applications with short term, spiky, or unpredictable workloads that cannot be interrupted ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("8 SSL Certificates");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("4 SSL Certificates");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("2 SSL Certificates");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("1 SSL Certificates");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "How many SSL Certificates can you associate with an ELB ( Elastic Load Balancer ) Instance ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("True");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("False");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("Does AWS VPC Peering support Transitive Peering - True or False ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        List<Color> colorList = new ArrayList<>();

        Color color1 = new Color();
        color1.setCode("#B66D18");
        colorList.add(color1);
        colorRepository.save(color1);

        Color color2 = new Color();
        color2.setCode("#984E03");
        colorList.add(color2);
        colorRepository.save(color2);

        Quiz quiz = new Quiz();
        quiz.setName("DevOps");
        quiz.setDescription("DevOps Tech Quiz");
        quiz.setType(Quiz.Type.TEST);
        quiz.setQuestions(questions);
        quiz.setColors(colorList);
        quizRepository.save(quiz);

        QuizResultMessage message = new QuizResultMessage();
        message.setQuiz(quiz);
        message.setRate(100);
        message.setMessage(
                "You’re a superhero! You got 100% right! Our team will definitely fall in love with you!");

        QuizResultMessage message1 = new QuizResultMessage();
        message1.setQuiz(quiz);
        message1.setRate(60);
        message1.setMessage(
                "Sooooo close. You got more than 60% right! Still, a great job well done");

        QuizResultMessage message2 = new QuizResultMessage();
        message2.setQuiz(quiz);
        message2.setRate(30);
        message2.setMessage(
                "Nothing worth having comes easy! You learn, you win, you lose, but you get up. NEVER give up!");

        QuizResultMessage message3 = new QuizResultMessage();
        message3.setQuiz(quiz);
        message3.setRate(0);
        message3.setMessage(
                "That was a tough test! But remember, there are no wrong answers, only new lessons! Remain curious and keep learning!");

        messageRepository.save(message);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        System.out.println("Generate Data Service: DevOps Quiz Generated.");
    }

    private void generateJavaQuiz() {
        Answer a;
        Question q;

        ArrayList<Answer> answers = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();

        a = new Answer();
        a.setText("[A, D, B, C]");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("[D, B, C]");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("[A, D, C]");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("[D, B12, C]");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("public class Vitamins {\n" + "\tpublic static void main(String[] args) {\n"
                          + "\t\tList<String> vitamins = new ArrayList<>();\n"
                          + "\t\tvitamins.add(\"A\");\n" + "\t\tvitamins.add(\"B12\");\n"
                          + "\t\tvitamins.add(\"C\");\n" + "\t\tvitamins.set(1, \"B\");\n"
                          + "\t\tvitamins.add(1, \"D\");\n" + "\t\tSystem.out.println(vitamins);\n"
                          + "\t}\n" + "}\n");
        q.setTitle("What is the result ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("It creates an object and object is eligible for garbage collection");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("It creates an object and object is not eligible for garbage collection");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText(
                "It creates two objects: e and e1. The e object is eligible for garbage collection");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText(
                "It creates two objects and both objects are not eligible for garbage collection");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("class Engine {\n" + "}\n" + " \n" + "public class App {\n"
                          + "\tpublic static void main(String[] args) {\n"
                          + "\t\tEngine e = new Engine();\n" + "\t\tEngine e1 = e;\n"
                          + "\t\te = null;\n" + "\t}\n" + "}\n");
        q.setTitle("Which statement is true about this code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("final class Cart");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("public class Cart");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("private class Cart");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("//Given this code fragment from Cart.java:\n" + " \n" + "package shop;\n" + " \n"
                          + "// line n1\n" + "{\n" + "\tCart() {\n"
                          + "\t    System.out.println(\"Cart is created\");\n" + "\t}\n" + "}\n"
                          + " \n" + "//And this content from OnlineCart.java:\n" + " \n"
                          + "package shop;\n" + " \n" + "public class OnlineCart extends Cart{\n"
                          + "\tpublic static void main(String[] args){\n"
                          + "\t\tCart c = new OnlineCart();\n" + "\t}\n" + "}\n");
        q.setTitle(
                "Which code fragment can be inserted at line n1 to enable the Shop to compile ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Replace line n1 with double wage = 2");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Replace line n2 with long monthDays = weekDays * 4L");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Replace line n3 with long yearDays = monthDays * 12");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Replace line n4 with long totalWage = yearDays * (long)wage");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("double wage = 2.0;\t\t\t\t// line n1\n" + "int weekDays = 5;\n"
                          + "long monthDays = weekDays * 4;\t\t\t// line n2\n"
                          + "long yearDays = monthDays * 12L;\t\t// line n3\n"
                          + "long totalWage = yearDays * wage;\t\t// line n4\n");
        q.setTitle("Which modification enables the code to compile ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Compile-time error");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints 1");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints 2");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints 3");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints 7");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints 8");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("public class Static {\n" + "\tstatic {\n" + "\t\tint x = 5;\n" + "\t}\n" + "\n"
                          + "\tstatic int x, y;\n" + "\n"
                          + "\tpublic static void main(String[] args) {\n" + "\t\tx--;\n"
                          + "\t\tmethod1();\n" + "\t\tSystem.out.println(x + y + ++x);\n" + "\t}\n"
                          + "\n" + "\tpublic static void method1() {\n" + "\t\ty = x++ + ++x;\n"
                          + "\t}\n" + "}\n");
        q.setTitle("What will happen when you attempt to compile and run the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("The code won't compile");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("\"Some things are true in this world\" will be printed");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("\"Hey this won't compile\" will be printed");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("None of these");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("boolean a = true;\n" + "boolean b = false;\n" + "boolean c = true;\n"
                          + "if (a == true)\n" + "\tif (b == true)\n" + "\t\tif (c == true)\n"
                          + "\t\t\tSystem.out.println(\"Some things are true in this world\");\n"
                          + "\t\telse\n"
                          + "\t\t\tSystem.out.println(\"Nothing is true in this world!\");\n"
                          + "\telse if (a && (b = c))\n"
                          + "\t\t\tSystem.out.println(\"It is too confusing to tell what is true and what is false\");\n"
                          + "\t\telse\n" + "\t\t\tSystem.out.println(\"Hey this won't compile\");");
        q.setTitle("What will be the result of executing the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("15 020");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("15 015");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("20 020");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("0 1520");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("class Value{\n" + "\tpublic int i = 15;\n" + "}\n" + " \n"
                          + "public class Test {\n" + "\tpublic static void main(String[] args) {\n"
                          + "\t\tTest t = new Test();\n" + "\t\tt.firstMethod();\n" + "\t}\n"
                          + "\tpublic void firstMethod(){\n" + "\t\tint i = 5;\n"
                          + "\t\tValue v = new Value();\n" + "\t\tv.i = 25;\n"
                          + "\t\tsecondMethod(v, i);\n" + "\t\tSystem.out.print(v.i);\n" + "\t}\n"
                          + " \n" + "\tpublic void secondMethod(Value v, int i) {\n"
                          + "\t\ti = 0;\n" + "\t\tv.i = 20;\n" + "\t\tValue value = new Value();\n"
                          + "\t\tv = value;\n" + "\t\tSystem.out.print(v.i + \" \" + i);\n"
                          + "\t}\n" + "}\n");
        q.setTitle("Given the following code, what will be the output ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("RuntimeException");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("VirtualMachineError");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("IllegalAccessException");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Throwable");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle("The class AssertionError has \"is-a\" relationship with these classes ?");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Prints: Value is - 9.0");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Prints: Value is - 5");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Сompilation error");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("None of these");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("public class Ternary {\n" + "\tpublic static void main(String[] args) {\n"
                          + "\t\tint a = 5;\n"
                          + "\t\tSystem.out.println(\"Value is - \" + ((a < 5) ? 9.9 : 9));\n"
                          + "\t}\n" + "}\n");
        q.setTitle("What will happen when you attempt to compile and run the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("100 will be printed 11 times");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("100 will be printed 10 times and then there will be a runtime exception");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText(
                "The code will not compile because the variable i cannot be declared twice within the main() method");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText(
                "The code will not compile because the variable j cannot be declared twice within the switch statement");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("None of these");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("public static void main(String[] args) {\n" + "\t\tchar digit = 'a';\n"
                          + "\t\tfor (int i = 0; i < 10; i++) {\n" + "\t\t\tswitch (digit) {\n"
                          + "\t\t\t\tcase 'x' : {\n" + "\t\t\t\t\tint j = 0;\n"
                          + "\t\t\t\t\tSystem.out.println(j);\n" + "\t\t\t\t}\n"
                          + "\t\t\t\tdefault: {\n" + "\t\t\t\t\tint j = 100;\n"
                          + "\t\t\t\t\tSystem.out.println(j);\n" + "\t\t\t\t}\n" + "\t\t\t}\n"
                          + "\t\t}\n" + "\t\tint i = j;\n" + "\t\tSystem.out.println(i);\n"
                          + "\t}\n");
        q.setTitle("What will be the result of executing the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Compilation error at line 5");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Compilation error at line 9");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Compilation error at line 11");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("None of these");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("//Filename: SuperclassX.java\n" + "package packageX;\n" + " \n"
                          + "public class SuperclassX {\n"
                          + "\tprotected void superclassMethodX(){\n" + "\t}\n" + " \n"
                          + "\tint superclassVarX;\n" + "}\n" + " \n"
                          + "//Filename: SubclassY.java\n" + "package packageX.packageY;\n"
                          + "import packageX.SuperclassX;\n" + " \n"
                          + "public class SubclassY extends SuperclassX {\n"
                          + "\tSuperclassX objX = new SubclassY();\n"
                          + "\tSubclassY objY = new SubclassY();\n" + " \n"
                          + "\tvoid subclassMethodY()\n" + "\t{\n"
                          + "\t\tobjY.superclassMethodX();\n" + "\t\tint i;\n"
                          + "\t\ti = objY.superclassVarX;\n" + "\t}\n" + "}\n");
        q.setTitle("What will be the result of executing the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Line 4 will not compile as void method can not be overridden");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("An exception at line 9");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText(
                "Line 9 will not compile as there is no version of myMethod which takes a char as argument");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("The code compiles and produces output: int version");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("The code compiles and produces output: String version");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setText("class MyClass {\n"
                          + "\tvoid myMethod(int i) {System.out.println(\"int version\");}\n"
                          + " \n"
                          + "\tvoid myMethod(String i) {System.out.println(\"String version\");}\n"
                          + " \n" + "\tpublic static void main(String[] args) {\n"
                          + "\t\tMyClass obj = new MyClass();\n" + "\t\tchar ch = 'c';\n"
                          + "\t\tobj.myMethod(ch);\n" + "\t}\n" + "}\n");
        q.setTitle("What will be the result of executing the following code ?");
        q.setAnswers(answers);
        q.setType(Question.Type.CODE);
        questions.add(q);
        questionRepository.save(q);

        List<Color> colorList = new ArrayList<>();

        Color c = new Color();
        c.setCode("#197E92");
        colorList.add(c);
        colorRepository.save(c);

        c = new Color();
        c.setCode("#095F73");
        colorList.add(c);
        colorRepository.save(c);

        Quiz quiz = new Quiz();
        quiz.setName("Java");
        quiz.setDescription("Java Tech Quiz");
        quiz.setType(Quiz.Type.TEST);
        quiz.setQuestions(questions);
        quiz.setColors(colorList);
        quizRepository.save(quiz);

        QuizResultMessage message = new QuizResultMessage();
        message.setQuiz(quiz);
        message.setRate(100);
        message.setMessage(
                "You’re a superhero! You got 100% right! Our team will definitely fall in love with you!");

        QuizResultMessage message1 = new QuizResultMessage();
        message1.setQuiz(quiz);
        message1.setRate(60);
        message1.setMessage(
                "Sooooo close. You got more than 60% right! Still, a great job well done");

        QuizResultMessage message2 = new QuizResultMessage();
        message2.setQuiz(quiz);
        message2.setRate(30);
        message2.setMessage(
                "Nothing worth having comes easy! You learn, you win, you lose, but you get up. NEVER give up!");

        QuizResultMessage message3 = new QuizResultMessage();
        message3.setQuiz(quiz);
        message3.setRate(0);
        message3.setMessage(
                "That was a tough test! But remember, there are no wrong answers, only new lessons! Remain curious and keep learning!");

        messageRepository.save(message);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        System.out.println("Generate Data Service: Java Quiz Generated.");
    }

    private void generateGeneralQuiz() {
        Answer a;
        Question q;

        ArrayList<Answer> answers = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();

        a = new Answer();
        a.setText("Gamedev");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Cloud");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("ProductDev");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Grid Dynamics is an Exclusive Partner for Lviv IT Arena. Select our technology");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("0000");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("0101");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("2006");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Find one code hidden in the conference hall that starts with GDXXXX and select the correct number from the written code");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Past");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Future");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Problem");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Stop by our booth and attend Live Demo session. Complete the secret phrase provided by a speaker at the end of the session: \"Artificial Intelligence is the ...\"");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Life");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Learning");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Label");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "One of our projects is called ML Sandbox. What does ‘ML’ stand for? Complete the last word for Machine ...");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Code");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Cake");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Calculation");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Check the Grid Dynamics meetup topics on October 1 and complete one of the topics with a proper word: Infrastructure as ...");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("GDLviv");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("UkraineGD");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Ukraine");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Find us on Facebook and complete the link below http://www.facebook.com/GridDynamics.");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        answers = new ArrayList<>();

        a = new Answer();
        a.setText("Interface");
        a.setCorrectly(true);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Infrastructure");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        a = new Answer();
        a.setText("Information");
        a.setCorrectly(false);
        answers.add(a);
        answerRepository.save(a);

        q = new Question();
        q.setTitle(
                "Check out our Tech Blog (blog.griddynamics.com) and type in what stands for CUI in our context. Select the right answer below for Conversational User ... (CUI)");
        q.setAnswers(answers);
        q.setType(Question.Type.TEXT);
        questions.add(q);
        questionRepository.save(q);

        List<Color> colorList = new ArrayList<>();

        Color c = new Color();
        c.setCode("#508721");
        colorList.add(c);
        colorRepository.save(c);

        c = new Color();
        c.setCode("#175A0A");
        colorList.add(c);
        colorRepository.save(c);

        Quiz quiz = new Quiz();
        quiz.setName("General");
        quiz.setDescription("General Quiz");
        quiz.setType(Quiz.Type.TEST);
        quiz.setQuestions(questions);
        quiz.setColors(colorList);
        quizRepository.save(quiz);

        QuizResultMessage message = new QuizResultMessage();
        message.setQuiz(quiz);
        message.setRate(100);
        message.setMessage(
                "You’re a superhero! You got 100% right! Our team will definitely fall in love with you!");

        QuizResultMessage message1 = new QuizResultMessage();
        message1.setQuiz(quiz);
        message1.setRate(60);
        message1.setMessage(
                "Sooooo close. You got more than 60% right! Still, a great job well done");

        QuizResultMessage message2 = new QuizResultMessage();
        message2.setQuiz(quiz);
        message2.setRate(30);
        message2.setMessage(
                "Nothing worth having comes easy! You learn, you win, you lose, but you get up. NEVER give up!");

        QuizResultMessage message3 = new QuizResultMessage();
        message3.setQuiz(quiz);
        message3.setRate(0);
        message3.setMessage(
                "That was a tough test! But remember, there are no wrong answers, only new lessons! Remain curious and keep learning!");

        messageRepository.save(message);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        System.out.println("Generate Data Service: General Quiz Generated.");
    }
}
