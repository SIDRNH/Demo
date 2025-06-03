package com.doodle.demo.controller;

import com.doodle.demo.model.Question;
import com.doodle.demo.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return ResponseEntity.ok(questionService.getAllQuestions());
        } catch (Exception e) {
            logger.error("Error occurred while fetching all questions", e);
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        try {
            Question question = questionService.getQuestionById(id);
            if (question != null) return ResponseEntity.ok(questionService.getQuestionById(id));
            else return ResponseEntity.notFound().build();
//            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error occurred while fetching the question with id: {}", id, e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        try {
            return ResponseEntity.ok(questionService.getQuestionsByCategory(category.toLowerCase()));
        } catch (Exception e) {
            logger.error("Error occurred while fetching questions by category: {}", category, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            Question createdQuestion = questionService.addQuestion(question);
            URI location = URI.create("/question/" + createdQuestion.getId());
            return ResponseEntity.created(location).body("Question added successfully with id: " + createdQuestion.getId());
        } catch (Exception e) {
            logger.error("Error occurred while adding the question", e);
            return ResponseEntity.badRequest().body("something went wrong");
        }
    }
}
