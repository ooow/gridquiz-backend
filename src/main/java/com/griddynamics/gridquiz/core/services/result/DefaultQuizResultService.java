package com.griddynamics.gridquiz.core.services.result;

import com.griddynamics.gridquiz.api.models.DashboardModel;
import com.griddynamics.gridquiz.api.models.NonApprovedModel;
import com.griddynamics.gridquiz.api.models.UserAnswersModel;
import com.griddynamics.gridquiz.api.models.UserResultModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.repository.*;
import com.griddynamics.gridquiz.repository.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Override
    public List<DashboardModel> generateDashboard() {
        return seq(quizDao.findAll())
                .map(q -> {
                            List<UserResult> top = resultDao.findTop5ByQuizAndApprovedOrderByPointsDesc(q, true);

                            top.sort((r1, r2) -> {
                                if (r1.getPoints() > r2.getPoints()) {
                                    return -1;
                                }
                                if (r1.getPoints() < r2.getPoints()) {
                                    return 1;
                                }
                                if (r1.getPoints() == r2.getPoints()) {
                                    if (firstBeFirst(r1, r2)) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                                return 0;
                            });


                            return new DashboardModel(q.getName(),
                                    seq(top).zipWithIndex()
                                            .map(r -> new DashboardModel.DashboardResultModel(
                                                    String.valueOf(r.v2),
                                                            r.v1.getUser().getName(),
                                                            String.valueOf(r.v1.getPoints()),
                                                            String.valueOf(getResultTime(r.v1.getStartTime(), r.v1.getEndTime()))
                                                    )
                                            ).toList()
                            );
                        }
                ).toList();
    }

    @Override
    public List<NonApprovedModel> nonApproved() {
        return seq(resultDao.findAllByApproved(false))
                .map(r -> new NonApprovedModel(r.getId(), r.getUser().getName(), r.getPoints(), r.getQuiz().getName()))
                .toList();
    }

    @Override
    public List<UserResultModel> getUsers() {
        return seq(userDao.findAllByRole(Role.USER))
                .map(u -> {
                    List<DashboardModel.DashboardResultModel> drm = seq(resultDao.findByUser(u))
                            .map(r -> new DashboardModel.DashboardResultModel(
                                            r.getQuiz().getName(),
                                            r.getUser().getName(),
                                            String.valueOf(r.getPoints()),
                                            String.valueOf(getResultTime(r.getStartTime(), r.getEndTime()))
                                    )
                            ).toList();

                    return new UserResultModel(u, drm);
                })
                .toList();
    }

    private boolean firstBeFirst(UserResult r1, UserResult r2) {
        return getResultTime(r1.getStartTime(), r1.getEndTime()) <= getResultTime(r2.getStartTime(), r2.getEndTime());
    }

    private long getResultTime(LocalDateTime t1, LocalDateTime t2) {
        if (Objects.nonNull(t1) && Objects.nonNull(t2))
            return Duration.between(t1, t2).toMillis();
        return -1;
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
