package com.griddynamics.gridquiz.core.services.result;

import static java.util.Objects.nonNull;
import static org.jooq.lambda.Seq.seq;

import com.griddynamics.gridquiz.api.models.common.NonApprovedModel;
import com.griddynamics.gridquiz.api.models.dashboard.DashboardModel;
import com.griddynamics.gridquiz.api.models.dashboard.DashboardResultModel;
import com.griddynamics.gridquiz.api.models.user.UserAnswersModel;
import com.griddynamics.gridquiz.api.models.user.UserDashboardResultModel;
import com.griddynamics.gridquiz.api.models.user.UserResultsModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.repository.AnswerDao;
import com.griddynamics.gridquiz.repository.QuestionDao;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.QuizResultMessageDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.QuizResultMessage;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.UserResult;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultQuizResultService implements QuizResultService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuizRepository quizDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuizResultMessageDao messageDao;

    @Override
    public UserResult calculateResult(List<UserAnswersModel> answers, String userToken) {
        //        Quiz quiz = seq(answers)
        //                .map(answer -> quizDao.findOne(answer.getQuizId()))
        //                .findFirst().orElseGet(null);
        //        UserResult result = seq(resultDao.findByUser(userDao.findByToken(userToken)))
        //                .filter(r -> r.getQuiz().equals(quiz))
        //                .findFirst()
        //                .orElse(null);
        //
        //        if (nonNull(result)) {
        //            int points;
        //            if (Quiz.Type.QUIZ.equals(quiz.getType())) {
        //                points = Math.toIntExact(
        //                        seq(answers)
        //                                .filter(a ->
        //                                        seq(questionDao.findOne(a.getQuestionId()).getAnswers())
        //                                                .map(Answer::getTextFieldAnswer)
        //                                                .anyMatch(a.getAnswer().toLowerCase()::equals))
        //                                .count());
        //            } else {
        //                points = Math.toIntExact(
        //                        seq(answers)
        //                                .filter(a -> answerDao.findOne(Long.valueOf(a.getAnswer())).isCorrectly())
        //                                .count());
        //            }
        //
        //            result.setPoints(points);
        //            result.setQuiz(quiz);
        //            result.setComment(getMessageForQuiz(quiz, points));
        //            result.setUser(userDao.findByToken(userToken));
        //            result.setEndTime(LocalDateTime.now());
        //            result.setApproved(false);
        //
        //            resultDao.save(result);
        //        }
        return null;
    }

    @Override
    public void startQuiz(Long quizId, String userToken) {
        //Quiz quiz = quizDao.findOne(quizId);
        Quiz quiz = null;
        Optional<UserResult> result = seq(resultDao.findByUser(userDao.findByToken(userToken)))
                .findFirst(r -> r.getQuiz().equals(quiz));
        if (!result.isPresent()) {
            resultDao.save(UserResult.builder()
                    .user(userDao.findByToken(userToken))
                    .quiz(quiz)
                    .startTime(LocalDateTime.now()).build());
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

                    return DashboardModel.builder()
                            .quizName(q.getName())
                            .results(seq(top).zipWithIndex()
                                    .map(r -> DashboardResultModel.builder()
                                            .position(String.valueOf(r.v2))
                                            .name(r.v1.getUser().getName())
                                            .result(String.valueOf(r.v1.getPoints()))
                                            .time(String.valueOf(getResultTime(r.v1.getStartTime(), r.v1.getEndTime())))
                                            .build())
                                    .toList())
                            .build();
                        }
                ).toList();
    }

    @Override
    public List<NonApprovedModel> nonApproved() {
        return seq(resultDao.findAllByApproved(false))
                .filter(r -> nonNull(r.getEndTime()))
                .map(r -> NonApprovedModel.builder()
                        .id(r.getId())
                        .name(r.getUser().getName())
                        .points(r.getPoints())
                        .quizName(r.getQuiz().getName())
                        .build())
                .toList();
    }

    @Override
    public List<UserDashboardResultModel> getUsers() {
        return seq(userDao.findAllByRole(Role.USER))
                .map(u -> UserDashboardResultModel.builder()
                        .user(u)
                        .results(seq(resultDao.findByUser(u))
                                .map(r -> DashboardResultModel.builder()
                                        .position(r.getQuiz().getName())
                                        .name(r.getUser().getName())
                                        .result(String.valueOf(r.getPoints()))
                                        .time(String.valueOf(getResultTime(r.getStartTime(), r.getEndTime())))
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public List<UserResultsModel> getUserResults() {
        return seq(userDao.findAllByRole(Role.USER))
                .map(u -> UserResultsModel.builder()
                        .user(u)
                        .results(resultDao.findByUser(u))
                        .build())
                .toList();
    }

    private boolean firstBeFirst(UserResult r1, UserResult r2) {
        return getResultTime(r1.getStartTime(), r1.getEndTime()) <= getResultTime(r2.getStartTime(), r2.getEndTime());
    }

    public static long getResultTime(LocalDateTime t1, LocalDateTime t2) {
        if (nonNull(t1) && nonNull(t2))
            return Duration.between(t1, t2).toMillis();
        return -1;
    }

    public static String toMinutes(long milliseconds) {
        return (int) ((milliseconds / (1000 * 60)) % 60) + ":" + (int) (milliseconds / 1000) % 60;
    }

    private QuizResultMessage getMessageForQuiz(Quiz quiz, int points) {
        List<QuizResultMessage> messages = messageDao.findByQuizId(quiz.getId());

        double percent = (points * 100) / quiz.getQuestions().size();

        return seq(messages)
                .findFirst(message -> percent >= message.getRate())
                .orElseGet(null);
    }
}
