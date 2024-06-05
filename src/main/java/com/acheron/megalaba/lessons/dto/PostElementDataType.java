package com.acheron.megalaba.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostElementDataType {
    private String element;
    private Long parentId ;
    private String title;
}
