package com.acheron.megalaba.security.dto;

import com.acheron.megalaba.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProps {
    private Long id;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private Integer score;
    private Float performance;
    private List<AccountTestProps> testList;
}
