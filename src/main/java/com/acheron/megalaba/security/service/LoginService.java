package com.acheron.megalaba.security.service;

import com.acheron.megalaba.security.controller.N;
import com.acheron.megalaba.security.dto.LoginRequest;
import com.acheron.megalaba.security.dto.RegistrationRequest;
import com.acheron.megalaba.security.entity.User;
import com.acheron.megalaba.security.exception.custom.EmailAlreadyExists;
import com.acheron.megalaba.security.exception.custom.PhoneNumberAlreadyExists;
import com.acheron.megalaba.security.exception.custom.UserNotFoundException;
import com.acheron.megalaba.security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public N registration(RegistrationRequest registrationRequest) {
        if (userService.findByEmail(registrationRequest.getEmail()).isEmpty()) {


            User user = userService.save(registrationRequest);
            Cookie cookie = createCookie(jwtUtil.generateToken(user));
            return new N(cookie,user.getId());
        } else
            throw new EmailAlreadyExists("User with email: " + registrationRequest.getEmail() + " is already registered");
    }

    public N login(LoginRequest request) throws BadRequestException {
        if (request.getEmail() != null && request.getPassword() != null) {
            try {
                authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        request.getEmail(),
                                        request.getPassword()));
            } catch (Exception e) {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new BadRequestException("Bad request");
        }
        N n = new N(createCookie(jwtUtil.generateToken(userService.findByEmail(request.getEmail()).orElseThrow())), userService.findByEmail(request.getEmail()).orElseThrow().getId());
        return n;
    }

    private Cookie createCookie(String jwt) {
        Cookie cookie = new Cookie("accessToken", jwt);
        cookie.setMaxAge(200000);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    public String findByEmail(String email) {
        userService.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
        return email;
    }

    public Cookie logout(HttpServletRequest request) {
        Cookie cookie = Arrays.stream(request.getCookies()).filter(cookie1 -> cookie1.getName().equals("accessToken")).findFirst().orElse(null);
        if (cookie != null) {
            cookie.setValue("null");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            return cookie;
        }
        return null;
    }
}
