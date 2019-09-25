package com.griddynamics.gridquiz.repository;

import com.griddynamics.gridquiz.repository.model.Result;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<Result, String> {
    List<Result> findAllByQuizId(String quizId);

    List<Result> findAllByQuizIdAndAndEndTimeIsNotNull(String quizId);

    List<Result> findAllByUserId(String userId);

    Optional<Result> findFirstByUserIdAndQuizId(String userId, String quizId);

    List<Result> findTop5ByQuizIdOrderByPointsDesc(String quizId);

    List<Result> findTop5ByQuizIdAndEndTimeIsNotNullOrderByPointsDesc(String quizId);

    Optional<List<Result>> removeByQuizId(String quizId);

    Optional<List<Result>> removeByUserId(String userId);
}
