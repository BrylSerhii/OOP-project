package com.acheron.megalaba.security.controller;


import com.acheron.megalaba.security.dto.*;
import com.acheron.megalaba.security.entity.User;
import com.acheron.megalaba.security.exception.custom.EmailAlreadyExists;
import com.acheron.megalaba.security.exception.custom.PhoneNumberAlreadyExists;
import com.acheron.megalaba.security.service.LoginService;
import com.acheron.megalaba.security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> register(@RequestBody(required = false) RegistrationRequest registrationRequest, HttpServletResponse response) {
        N registration1=null;
        try {
            registration1 = loginService.registration(registrationRequest);
            Cookie registration = registration1.getCookie();
            response.addCookie(registration);
        } catch (EmailAlreadyExists | PhoneNumberAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(new Id(registration1.getId()));
    }


    @PostMapping("/login-user")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        N login=null;
        try {
            login = loginService.login(request);
            response.addCookie(login.getCookie());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Bad request");
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Bad credentials");
        }
        return ResponseEntity.ok(new Id(login.getId()));
    } @GetMapping("/account/{id}")
    public ResponseEntity<?> account(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id).map(
                e->new AccountProps(e.getId(),e.getRole().getAuthority().toLowerCase(),e.getEmail(),e.getFirstName(), e.getLastName(),e.getScore(),e.getPerformance(), e.getUserTests().stream().map(e1->new AccountTestProps(e1.getId(),e1.getTest().getTitle(),e1.getTest().getPoints(),e1.getReceivedPoints())).toList())));
    }

    @PostMapping("/logout-user")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = loginService.logout(request);
        if (cookie != null) {
            response.addCookie(cookie);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("user did not log in");
    } @DeleteMapping("/delete-user")
    public ResponseEntity<?> delete(Principal principal) {
        var byEmail = userService.findByEmail(principal.getName()).orElseThrow();
        userService.delete(byEmail.getId());
        return ResponseEntity.ok("success");
    }


}
