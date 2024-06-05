package com.acheron.megalaba.lessons.controller;

import com.acheron.megalaba.lessons.dto.QuizDto;
import com.acheron.megalaba.lessons.dto.SubmitTestResponseData;
import com.acheron.megalaba.lessons.dto.TestBody;
import com.acheron.megalaba.lessons.entity.Quiz;
import com.acheron.megalaba.lessons.entity.Test;
import com.acheron.megalaba.lessons.entity.UserTest;
import com.acheron.megalaba.lessons.repository.TestRepository;
import com.acheron.megalaba.lessons.repository.UserTestRepository;
import com.acheron.megalaba.security.entity.User;
import com.acheron.megalaba.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserTestController {
    private final UserTestRepository userTestRepository;
    private final UserService userService;
    private final TestRepository testRepository;

    @PostMapping("/submit-test")
    public ResponseEntity<?> test(@RequestBody(required = false) TestBody testBody, Principal principal){
        Test test = testRepository.findById(testBody.getTestId()).orElseThrow();
        List<Quiz> quizzes1 = test.getQuizzes();
        if(testBody.getQuizzes()==null || quizzes1 == null){
            return ResponseEntity.badRequest().body("sda");
        }
        var quizzes = quizzes1.stream()
                .collect(Collectors.toMap(Quiz::getId, Quiz::getTrueOption));
        AtomicInteger counter = new AtomicInteger();
        Map<String, String> resultMap = testBody.getQuizzes().stream()
                .collect(Collectors.toMap(
                        e4 -> e4[0],  // key mapper
                        e4 -> e4[1]   // value mapper
                ));
        System.out.println(quizzes1);
        resultMap.forEach((e,e1)->{
            if (quizzes.get(Long.valueOf(e)).equals(e1)){
                Integer points = quizzes1.stream().filter(e3 -> e3.getId().equals(Long.valueOf(e))).findFirst().get().getPoints();
                counter.addAndGet(points);
            }
        });
//        resultMap.forEach((e,e1)->{
//            if (quizzes.get(Long.valueOf(e[0])).equals(e1)){
//                Integer points = quizzes1.stream().filter(e3 -> e3.getId().equals(e)).findFirst().get().getPoints();
//                counter.addAndGet(points);
//            }
//        });
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        UserTest userTest = new UserTest(null,user , test, counter.get(), counter.floatValue() / (test.getPoints() != null ? test.getPoints() : 1),(test.getPoints() != null ? test.getPoints() : 1));
        System.out.println(userTest);
        userTestRepository.save(userTest);
        user.setPerformance((user.getPerformance()!=null?user.getPerformance():0)+userTest.getPerformance());
        user.setScore((user.getScore()!=null?user.getScore():0)+ userTest.getPoints());
        userService.save(user);
        return ResponseEntity.ok(new SubmitTestResponseData(test.getId(), test.getTitle(),counter.get(),test.getPoints()));

    }
}
