package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<UserResult, String> {
    List<UserResult> removeByUser(User user);

    List<UserResult> findByQuiz(Quiz quiz);

    List<UserResult> findByUser(User user);

    List<UserResult> findTop5ByQuizAndApprovedOrderByPointsDesc(Quiz quiz, boolean approved);

    List<UserResult> findAllByApproved(boolean approved);
}
