package com.acheron.megalaba.lessons.dto;

import com.acheron.megalaba.lessons.entity.Theory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    private List<TheoryDto> theoryList;
    private List<TestDto> testList;
}
