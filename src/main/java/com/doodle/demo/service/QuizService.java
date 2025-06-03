package com.doodle.demo.service;

import com.doodle.demo.dao.QuestionDao;
import com.doodle.demo.dao.QuizDao;
import com.doodle.demo.model.Question;
import com.doodle.demo.model.Quiz;
import com.doodle.demo.model.SubmitQuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Integer calculateResult(Integer id, List<SubmitQuizResponse> responses) {
        Quiz quiz = quizdao.findById(id).orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        Map<Integer, String> answerKey = quiz.getQuestions().stream().collect(Collectors.toMap(
                Question::getId, Question::getRightAnswer
        ));
        int marks = 0;
        for (SubmitQuizResponse response : responses) {
            String correctAnswer = answerKey.get(response.getId());
            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(response.getSubmittedAnswer())) marks++;
        }
        return marks;
    }
}
