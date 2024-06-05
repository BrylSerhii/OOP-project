package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostQuizType {
    private Long parentTestId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String correctOption;
    private Integer points;
}
