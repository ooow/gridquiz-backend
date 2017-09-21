package com.griddynamics.gridquiz.common;

import com.griddynamics.gridquiz.repository.*;
import com.griddynamics.gridquiz.repository.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateDateService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private ColorDao colorDao;

    @Autowired
    private QuizResultMessageDao messageDao;

    public void generate() {
/*        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("ttt1@mail.ru");
        user1.setPhone("+992473923743");
        user1.setToken(TokenGenerator.generateToken(user1.getEmail() + user1.getPhone()));
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setName("user2");
        user2.setEmail("ttt2@mail.ru");
        user2.setPhone("+981234323752");
        user2.setToken(TokenGenerator.generateToken(user2.getEmail() + user2.getPhone()));
        user2.setRole(Role.USER);

        User user3 = new User();
        user3.setName("user3");
        user3.setEmail("ttt3@mail.ru");
        user3.setPhone("+941373923712");
        user3.setToken(TokenGenerator.generateToken(user3.getEmail() + user3.getPhone()));
        user3.setRole(Role.USER);

        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);*/


        ArrayList<Answer> answers = new ArrayList<>();

        Answer answer1 = new Answer();
        answer1.setText("text1");
        answer1.setCorrectly(false);
        answers.add(answer1);
        answerDao.save(answer1);

        Answer answer2 = new Answer();
        answer2.setText("text2");
        answer2.setCorrectly(false);
        answers.add(answer2);
        answerDao.save(answer2);


        Answer answer3 = new Answer();
        answer3.setText("text3");
        answer3.setCorrectly(true);
        answers.add(answer3);
        answerDao.save(answer3);


        Answer answer4 = new Answer();
        answer4.setText("text4");
        answer4.setCorrectly(false);
        answers.add(answer4);
        answerDao.save(answer4);


        ArrayList<Question> questions = new ArrayList<>();

        Question question1 = new Question();
        question1.setText("Some text");
        question1.setTitle("Some Title");
        question1.setAnswers(answers);
        questions.add(question1);
        questionDao.save(question1);

        answers = new ArrayList<>();

        Answer answer5 = new Answer();
        answer5.setText("text5");
        answer5.setCorrectly(false);
        answers.add(answer5);
        answerDao.save(answer5);


        Answer answer6 = new Answer();
        answer6.setText("text6");
        answer6.setCorrectly(true);
        answers.add(answer6);
        answerDao.save(answer6);


        Answer answer7 = new Answer();
        answer7.setText("text7");
        answer7.setCorrectly(false);
        answers.add(answer7);
        answerDao.save(answer7);


        Answer answer8 = new Answer();
        answer8.setText("text8");
        answer8.setCorrectly(false);
        answers.add(answer8);
        answerDao.save(answer8);

        Question question2 = new Question();
        question2.setText("Some text 2");
        question2.setTitle("Some Title 2");
        question2.setAnswers(answers);
        questions.add(question2);
        questionDao.save(question2);

        answers = new ArrayList<>();

        answer5 = new Answer();
        answer5.setText("text5");
        answer5.setCorrectly(false);
        answers.add(answer5);
        answerDao.save(answer5);


        answer6 = new Answer();
        answer6.setText("text6");
        answer6.setCorrectly(true);
        answers.add(answer6);
        answerDao.save(answer6);


        answer7 = new Answer();
        answer7.setText("text7");
        answer7.setCorrectly(false);
        answers.add(answer7);
        answerDao.save(answer7);


        answer8 = new Answer();
        answer8.setText("text8");
        answer8.setCorrectly(false);
        answers.add(answer8);
        answerDao.save(answer8);


        question2 = new Question();
        question2.setText("Some text 2");
        question2.setTitle("Some Title 2");
        question2.setAnswers(answers);
        questions.add(question2);
        questionDao.save(question2);

        List<Color> colorList = new ArrayList<>();

        Color color1 = new Color();
        color1.setCode("#EA3440");
        colorList.add(color1);
        colorDao.save(color1);

        Color color2 = new Color();
        color2.setCode("#DA3A4C");
        colorList.add(color2);
        colorDao.save(color2);


        Quiz quiz = new Quiz();
        quiz.setName("Some Name");
        quiz.setDescription("Some Des");
        quiz.setQuestions(questions);
        quiz.setColors(colorList);
        quizDao.save(quiz);

        QuizResultMessage message = new QuizResultMessage();
        message.setQuiz(quiz);
        message.setRate(100);
        message.setMessage("You’re a superhero! You got 100% right! Our team will definitely fall in love with you!");

        QuizResultMessage message1 = new QuizResultMessage();
        message1.setQuiz(quiz);
        message1.setRate(60);
        message1.setMessage("Sooooo close. You got more than 60% right! Still, a great job well done");

        QuizResultMessage message2 = new QuizResultMessage();
        message2.setQuiz(quiz);
        message2.setRate(30);
        message2.setMessage("Nothing worth having comes easy! You learn, you win, you lose, but you get up. NEVER give up!");

        QuizResultMessage message3 = new QuizResultMessage();
        message3.setQuiz(quiz);
        message3.setRate(0);
        message3.setMessage("That was a tough test! But remember, there are no wrong answers, only new lessons! Remain curious and keep learning!");

        messageDao.save(message);
        messageDao.save(message1);
        messageDao.save(message2);
        messageDao.save(message3);
    }
}
