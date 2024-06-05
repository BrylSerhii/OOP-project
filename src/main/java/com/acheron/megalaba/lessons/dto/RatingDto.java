package com.acheron.megalaba.lessons.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    private Long id;

    private String firstName;

    private String lastName;
    private Integer score;
    private Float performance;
}
