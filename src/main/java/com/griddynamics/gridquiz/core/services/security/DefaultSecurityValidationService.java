package com.griddynamics.gridquiz.core.services.security;

import static java.util.Objects.isNull;

import com.griddynamics.gridquiz.core.services.SecurityValidationService;
import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSecurityValidationService implements SecurityValidationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    QuizRepository quizRepository;

    @Override
    public void canStartQuiz(Long quizId, String userToken) {
        validateToken(userToken);

        //        Optional<UserResult> result = seq(resultRepository.findByQuiz(quizRepository.findById(quizId)))
        //                .findFirst(r -> r.getUser().getToken().equals(userToken));
        //
        //        if (result.isPresent() && nonNull(result.get().getEndTime())) {
        //            throw new SecurityValidationException("Quiz already complete.");
        //        }
    }

    @Override
    public void validateToken(String userToken) {
        if (isNull(userRepository.findByToken(userToken))) {
            throw new SecurityValidationException("User not found.");
        }
    }

    @Override
    public void isAdmin(String userToken) {
        User user = userRepository.findByToken(userToken);
        if (isNull(user) || !Role.ADMIN.equals(user.getRole())) {
            throw new SecurityValidationException("Permission denied.");
        }
    }
}
