package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBody {

    private Long testId;
    private List<String[]> quizzes;


}
