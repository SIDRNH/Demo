package com.doodle.demo.service;

import com.doodle.demo.dao.QuestionDao;
import com.doodle.demo.dao.QuizDao;
import com.doodle.demo.model.Question;
import com.doodle.demo.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizdao;
    @Autowired
    QuestionDao questionDao;

    public Quiz getQuizById(int id) {
        return quizdao.findById(id).orElse(null);
    }

    public Quiz createQuiz(String title, String category, int numberOfQuestions) {
        List<Question> questions = questionDao.findQuestionsByCategory(category, numberOfQuestions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        System.out.println("quiz service: " + questions);
        System.out.println("quiz service: " + quiz);
        return quizdao.save(quiz);
    }
}
