package com.doodle.demo.dto;

import com.doodle.demo.model.QuestionWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQuizResponseDTO {
    private Integer id;
    private String title;
    private List<QuestionWrapper> questions;
}
/*
DTOs are created to send a limited data through api
they are helpers classes that will help to exclude sensitive data in the response.
 */
