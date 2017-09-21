package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.repository.QuizDao;
import com.griddynamics.gridquiz.repository.ResultDao;
import com.griddynamics.gridquiz.repository.UserDao;
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
    public boolean canStartQuiz(Long quizId, String userToken) {
        User user = userDao.findByToken(userToken);

        if (!Objects.nonNull(user)) {
            return false;
        }

        UserResult result = seq(resultDao.findByQuiz(quizDao.findOne(quizId)))
                .filter(res -> res.getUser().getToken().equals(userToken))
                .findFirst()
                .orElse(null);

        return !Objects.nonNull(result) || Objects.isNull(result.getEndTime());
    }

    @Override
    public boolean validateToken(String userToken) {
        return Objects.isNull(userDao.findByToken(userToken));
    }
}
