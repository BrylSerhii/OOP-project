package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizCreateDto {
    private Long test;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String trueOption;
    private Integer points;
}
