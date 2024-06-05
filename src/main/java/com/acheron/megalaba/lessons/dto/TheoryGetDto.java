package com.acheron.megalaba.lessons.dto;

import com.acheron.megalaba.lessons.entity.Lesson;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheoryGetDto {
    private Long id;
    private String title;
    private String html;
    private String author;
}
