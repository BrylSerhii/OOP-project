package com.acheron.megalaba.lessons.controller;

import com.acheron.megalaba.lessons.dto.RatingDto;
import com.acheron.megalaba.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RatingController {
    private final UserService userService;

    @GetMapping("/rating")
    public ResponseEntity<?> getRating(@RequestParam(required = false) String sort){
        return ResponseEntity.ok(userService.findAllUsers(Sort.by(Sort.Direction.DESC,sort)).stream().map(e->new RatingDto(e.getId(), e.getFirstName(), e.getLastName(),e.getScore(),e.getPerformance())));
    }
}
