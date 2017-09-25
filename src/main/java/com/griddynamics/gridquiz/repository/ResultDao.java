package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.models.Quiz;
import com.griddynamics.gridquiz.repository.models.User;
import com.griddynamics.gridquiz.repository.models.UserResult;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ResultDao extends CrudRepository<UserResult, Long> {
    List<UserResult> removeByUser(User user);

    List<UserResult> findByQuiz(Quiz quiz);

    List<UserResult> findByUser(User user);

    List<UserResult> findTop5ByQuizAndApprovedOrderByPointsDesc(Quiz quiz, boolean approved);

    List<UserResult> findAllByApproved(boolean approved);
}
