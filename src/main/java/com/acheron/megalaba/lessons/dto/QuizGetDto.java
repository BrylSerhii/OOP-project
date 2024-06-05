package com.acheron.megalaba.lessons.dto;

import com.acheron.megalaba.lessons.entity.Test;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizGetDto {
    private Long id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private Integer points;
}
