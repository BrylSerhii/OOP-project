package com.acheron.megalaba.security.service;


import com.acheron.megalaba.security.dto.*;
import com.acheron.megalaba.security.entity.Role;
import com.acheron.megalaba.security.entity.User;
import com.acheron.megalaba.security.exception.custom.UserAlreadyExistsException;
import com.acheron.megalaba.security.mapper.UserMapper;
import com.acheron.megalaba.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<User> findAllUsers(Sort sort) {
        return userRepository.findAllByRole(Role.USER.getAuthority(),sort);
    }

    public ResponseEntity<?> getCurrentUser(Principal principal) {
        User user = findByEmail(principal != null ? principal.getName() : null).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(new UserContextProps(user.getFirstName(),user.getLastName(),user.getId(),user.getRole().getAuthority().toLowerCase()));
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    public ResponseEntity<?> changeUser(Principal principal, UserChangeDto userChangeDto) {
        User user = findByEmail(principal.getName()).orElse(null);
        if (user != null) {
            User newUser = save(userMapper.mapFromUserChangeDto(userChangeDto, user));
            return ResponseEntity.ok(new Id(newUser.getId()));
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    public ResponseEntity<String> changePassword(Principal principal, PasswordChangeDto passwordChangeDto, AuthenticationManager authenticationManager) {
        User user = findByEmail(principal.getName()).orElse(null);
        if (user != null && passwordChangeDto.getCurrentPassword() != null) {
            try {
                authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        passwordChangeDto.getCurrentPassword()));
            } catch (Exception e) {
                throw new BadCredentialsException("Bad credentials");
            }
            if (passwordChangeDto.getNewPassword() != null) {
//                user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
                save(user);
                return ResponseEntity.ok("Successful");
            } else {
                return ResponseEntity.badRequest().body("Bad request");
            }
        }
        return ResponseEntity.badRequest().body("Bad request");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(RegistrationRequest request) throws UserAlreadyExistsException {
        if (userAlreadyExists(request.getEmail())) {
            String password = passwordEncoder.encode(request.getPassword());
            return userRepository.save(new User(null, request.getFirstName(), request.getLastName(), request.getEmail(), password, null, null, null, Role.USER));
        } else {
            throw new UserAlreadyExistsException();
        }

    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
