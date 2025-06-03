package com.doodle.demo.controller;

import com.doodle.demo.dto.GetQuizResponseDTO;
import com.doodle.demo.model.QuestionWrapper;
import com.doodle.demo.model.Quiz;
import com.doodle.demo.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    QuizService quizService;

    @GetMapping("{id}")
    public ResponseEntity<GetQuizResponseDTO> getQuizById(@PathVariable int id) {
        try {
            Quiz quiz = quizService.getQuizById(id);
            if (quiz == null) return ResponseEntity.notFound().build();
            List<QuestionWrapper> questionWrappers = quiz.getQuestions().stream().map(q -> new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            )).toList();
            GetQuizResponseDTO response = new GetQuizResponseDTO(quiz.getId(), quiz.getTitle(), questionWrappers);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            logger.error("Error occurred while fetching the quiz with id: {}", id, e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam(name = "numQ") int numberOfQuestions, @RequestParam String title) {
        try {
            Quiz createdQuiz = quizService.createQuiz(title, category.toLowerCase(), numberOfQuestions);
            URI location = URI.create("quiz/" + createdQuiz.getId());
            System.out.println("Created Quiz: " + createdQuiz);
            return ResponseEntity.created(location).body("Quiz created Successfully with ID: " + createdQuiz.getId());
        } catch (Exception e) {
            logger.error("Error occurred while creating quiz", e);
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
