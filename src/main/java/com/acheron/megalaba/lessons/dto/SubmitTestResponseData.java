package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTestResponseData {
    private Long id;
    private String title;
    private Integer receivedPoints;
    private Integer points;
}
