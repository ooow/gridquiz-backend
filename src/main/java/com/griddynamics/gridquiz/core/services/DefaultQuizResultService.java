package com.griddynamics.gridquiz.core.services;

import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.repository.*;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.lambda.Seq.seq;

@Service
public class DefaultQuizResultService implements QuizResultService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private QuizResultMessageDao messageDao;

    @Override
    public UserResult calculateResult(List<UserAnswersModel> answers) {
        UserResult result = new UserResult();
        Long points = seq(answers).filter(answer -> answerDao.findOne(answer.getAnswerId()).isCorrectly()).count();
        Quiz quiz = seq(answers)
                .map(answer -> quizDao.findOne(answer.getQuizId()))
                .findFirst().orElseGet(null);

        result.setPoints(points);
        result.setQuiz(quiz);
        result.setComment(getMessageForQuiz(quiz, points));

        //todo fix me please :)
        result.setStartTime(1L);
        result.setEndTime(1L);
        result.setUser(userDao.findOne(1L));

        resultDao.save(result);

        return result;
    }

    private QuizResultMessage getMessageForQuiz(Quiz quiz, Long points) {
        List<QuizResultMessage> messages = messageDao.findByQuizId(quiz.getId());

        double percent = (points * 100) / quiz.getQuestions().size();

        return seq(messages)
                .filter(message -> percent >= message.getRate())
                .findFirst()
                .orElseGet(null);
    }
}
