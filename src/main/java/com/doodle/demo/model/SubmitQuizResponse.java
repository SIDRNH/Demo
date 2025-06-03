package com.doodle.demo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SubmitQuizResponse {
    private Integer id;
    private String submittedAnswer;
}
