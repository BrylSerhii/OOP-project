package com.acheron.megalaba.lessons.dto;

import com.acheron.megalaba.lessons.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestGetDto {
    private Long id;
    private String title;
    private Integer points;
    private String author;
    private String lessonTitle;
    private List<QuizGetDto> quizes;
}
