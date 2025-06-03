package com.doodle.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//This calls will limit the data which we send to the client
@Data
@AllArgsConstructor
public class QuestionWrapper {
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
