package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.Result;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<Result, String> {
    Optional<List<Result>> findAllByApproved(boolean approved);

    Optional<List<Result>> findByQuizId(String quizId);

    List<Result> findAllBy(String userId);

    Optional<Result> findFirstByUserIdAndQuizId(String userId, String quizId);

    Optional<List<Result>> findTop5ByQuizIdAndApprovedOrderByPointsDesc(String quizId,
                                                                        boolean approved);

    Optional<List<Result>> removeByQuizId(String quizId);

    Optional<List<Result>> removeByUserId(String userId);
}
