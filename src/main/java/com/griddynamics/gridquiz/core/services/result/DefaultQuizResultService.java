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
import com.griddynamics.gridquiz.repository.AnswerRepository;
import com.griddynamics.gridquiz.repository.QuestionRepository;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.QuizResultMessageRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizResultMessageRepository messageRepository;

    @Override
    public UserResult calculateResult(List<UserAnswersModel> answers, String userToken) {
        //        Quiz quiz = seq(answers)
        //                .map(answer -> quizRepository.findOne(answer.getQuizId()))
        //                .findFirst().orElseGet(null);
        //        UserResult result = seq(resultRepository.findByUser(userRepository.findByToken(userToken)))
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
        //                                        seq(questionRepository.findOne(a.getQuestionId()).getAnswers())
        //                                                .map(Answer::getTextFieldAnswer)
        //                                                .anyMatch(a.getAnswer().toLowerCase()::equals))
        //                                .count());
        //            } else {
        //                points = Math.toIntExact(
        //                        seq(answers)
        //                                .filter(a -> answerRepository.findOne(Long.valueOf(a.getAnswer())).isCorrectly())
        //                                .count());
        //            }
        //
        //            result.setPoints(points);
        //            result.setQuiz(quiz);
        //            result.setComment(getMessageForQuiz(quiz, points));
        //            result.setUser(userRepository.findByToken(userToken));
        //            result.setEndTime(LocalDateTime.now());
        //            result.setApproved(false);
        //
        //            resultRepository.save(result);
        //        }
        return null;
    }

    @Override
    public void startQuiz(Long quizId, String userToken) {
        //Quiz quiz = quizRepository.findOne(quizId);
        Quiz quiz = null;
        Optional<UserResult> result =
                seq(resultRepository.findByUser(userRepository.findByToken(userToken)))
                .findFirst(r -> r.getQuiz().equals(quiz));
        if (!result.isPresent()) {
            resultRepository.save(UserResult.builder().user(userRepository.findByToken(userToken))
                    .quiz(quiz)
                    .startTime(LocalDateTime.now()).build());
        }
    }

    @Override
    public List<DashboardModel> generateDashboard() {
        return seq(quizRepository.findAll())
                .map(q -> {
                    List<UserResult> top =
                            resultRepository.findTop5ByQuizAndApprovedOrderByPointsDesc(q, true);
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
        return seq(resultRepository.findAllByApproved(false))
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
        return seq(userRepository.findAllByRole(Role.USER))
                .map(u -> UserDashboardResultModel.builder()
                        .user(u).results(seq(resultRepository.findByUser(u))
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
        return seq(userRepository.findAllByRole(Role.USER))
                .map(u -> UserResultsModel.builder()
                        .user(u).results(resultRepository.findByUser(u))
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
        List<QuizResultMessage> messages = messageRepository.findByQuizId(quiz.getId());

        double percent = (points * 100) / quiz.getQuestions().size();

        return seq(messages)
                .findFirst(message -> percent >= message.getRate())
                .orElseGet(null);
    }
}
