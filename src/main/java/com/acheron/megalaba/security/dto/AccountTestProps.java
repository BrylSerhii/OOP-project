package com.acheron.megalaba.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTestProps {
    private Long id;
    private String title;
    private Integer points;
    private Integer receivedPoints;
}
