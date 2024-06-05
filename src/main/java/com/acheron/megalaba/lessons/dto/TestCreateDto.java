package com.acheron.megalaba.lessons.dto;

import com.acheron.megalaba.lessons.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestCreateDto {
    private String title;
    private Long lesson;
    private List<QuizCreateDto> quizzes;
}
