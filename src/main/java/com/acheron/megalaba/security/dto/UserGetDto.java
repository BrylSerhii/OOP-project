package com.acheron.megalaba.security.dto;

import com.acheron.megalaba.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
