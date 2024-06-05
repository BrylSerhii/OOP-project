package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private String title;
    private Integer questionsAmount;
    private Integer points;
}
