package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.jooq.lambda.Seq.seq;

@Service
public class DefaultSecurityValidationService implements SecurityValidationService {

    @Autowired
    UserDao userDao;

    @Autowired
    ResultDao resultDao;

    @Autowired
    QuizDao quizDao;

    @Override
    public void canStartQuiz(Long quizId, String userToken) {
        validateToken(userToken);

        UserResult result = seq(resultDao.findByQuiz(quizDao.findOne(quizId)))
                .filter(res -> res.getUser().getToken().equals(userToken))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(result) && Objects.nonNull(result.getEndTime())) {
            throw new SecurityValidationException("Quiz already complete.");
        }
    }

    @Override
    public void validateToken(String userToken) {
        if (Objects.isNull(userDao.findByToken(userToken))) {
            throw new SecurityValidationException("User not found.");
        }
    }

    @Override
    public void isAdmin(String userToken) {
        User user = userDao.findByToken(userToken);
        if (Objects.isNull(user) || !Role.ADMIN.equals(user.getRole())) {
            throw new SecurityValidationException("Permission denied.");
        }
    }
}
