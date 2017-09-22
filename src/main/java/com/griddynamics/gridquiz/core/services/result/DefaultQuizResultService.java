package com.griddynamics.gridquiz.core.services.result;

import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.repository.*;
import com.griddynamics.gridquiz.repository.models.Answer;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    private QuestionDao questionDao;

    @Autowired
    private QuizResultMessageDao messageDao;

    @Override
    public UserResult calculateResult(List<UserAnswersModel> answers, String userToken) {
        Quiz quiz = seq(answers)
                .map(answer -> quizDao.findOne(answer.getQuizId()))
                .findFirst().orElseGet(null);
        UserResult result = seq(resultDao.findByUser(userDao.findByToken(userToken)))
                .filter(r -> r.getQuiz().equals(quiz))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(result)) {

            int points;
            if (Quiz.Type.QUIZ.equals(quiz.getType())) {
                points = Math.toIntExact(
                        seq(answers)
                                .filter(a ->
                                        seq(questionDao.findOne(a.getQuestionId()).getAnswers())
                                                .map(Answer::getTextFieldAnswer)
                                                .anyMatch(a.getAnswer().toLowerCase()::equals))
                                .count());
            } else {
                points = Math.toIntExact(
                        seq(answers)
                                .filter(a -> answerDao.findOne(Long.valueOf(a.getAnswer())).isCorrectly())
                                .count());
            }

            result.setPoints(points);
            result.setQuiz(quiz);
            result.setComment(getMessageForQuiz(quiz, points));
            result.setUser(userDao.findByToken(userToken));
            result.setEndTime(LocalDateTime.now());
            result.setApproved(false);

            resultDao.save(result);
        }
        return result;
    }

    @Override
    public void startQuiz(Long quizId, String userToken) {
        Quiz quiz = quizDao.findOne(quizId);
        UserResult result = seq(resultDao.findByUser(userDao.findByToken(userToken)))
                .filter(r -> r.getQuiz().equals(quiz))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(result)) {
            result = new UserResult();
            result.setUser(userDao.findByToken(userToken));
            result.setQuiz(quiz);
            result.setStartTime(LocalDateTime.now());

            resultDao.save(result);
        }
    }

    private QuizResultMessage getMessageForQuiz(Quiz quiz, int points) {
        List<QuizResultMessage> messages = messageDao.findByQuizId(quiz.getId());

        double percent = (points * 100) / quiz.getQuestions().size();

        return seq(messages)
                .filter(message -> percent >= message.getRate())
                .findFirst()
                .orElseGet(null);
    }
}
