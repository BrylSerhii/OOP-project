package com.acheron.megalaba.security.controller;

import com.acheron.megalaba.security.dto.PasswordChangeDto;
import com.acheron.megalaba.security.dto.UserChangeDto;
import com.acheron.megalaba.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/current-user")
    public ResponseEntity<?> getUser(Principal principal) {
        return userService.getCurrentUser(principal);
    }

    @PatchMapping("/patch-user")
    public ResponseEntity<?> changeUser(Principal principal, @RequestBody UserChangeDto userChangeDto) {
        return userService.changeUser(principal,userChangeDto);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> changePassword(Principal principal, @RequestBody PasswordChangeDto passwordChangeDto) {
       return userService.changePassword(principal,passwordChangeDto,authenticationManager);
    }



}
