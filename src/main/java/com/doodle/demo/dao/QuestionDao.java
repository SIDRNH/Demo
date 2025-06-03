package com.doodle.demo.dao;

import com.doodle.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category = ?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
    List<Question> findQuestionsByCategory(String category, int numberOfQuestions);

    /*
    Query can also be written in 2 more ways they are:

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numberOfQuestions", nativeQuery = true)
    List<Question> findQuestionsByCategory(String category, int numberOfQuestions);

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findQuestionsByCategory(@Param("category) String category, @Param("limit) int numberOfQuestions);
     */
}
