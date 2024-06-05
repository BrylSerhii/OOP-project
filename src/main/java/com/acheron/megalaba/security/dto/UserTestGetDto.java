package com.acheron.megalaba.security.dto;

import com.acheron.megalaba.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestGetDto {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Long id;
    private List<AccountTestProps> testList;
}
