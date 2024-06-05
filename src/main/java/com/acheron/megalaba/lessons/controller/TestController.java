package com.acheron.megalaba.lessons.controller;

import com.acheron.megalaba.lessons.dto.*;
import com.acheron.megalaba.lessons.entity.Lesson;
import com.acheron.megalaba.lessons.entity.Quiz;
import com.acheron.megalaba.lessons.entity.Test;
import com.acheron.megalaba.lessons.entity.Theory;
import com.acheron.megalaba.lessons.repository.LessonRepository;
import com.acheron.megalaba.lessons.repository.QuizRepository;
import com.acheron.megalaba.lessons.repository.TestRepository;
import com.acheron.megalaba.lessons.repository.TheoryRepository;
import com.acheron.megalaba.security.entity.User;
import com.acheron.megalaba.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final QuizRepository quizRepository;
    private final TheoryRepository theoryRepository;

    @PostMapping("/create_test")
    public ResponseEntity<?> createTest(@RequestBody TestCreateDto testDto, Principal principal){
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(testRepository.save(new Test(null,testDto.getTitle(),0,0, user.getFirstName() + " " + user.getLastName(), lessonRepository.findById(testDto.getLesson()).orElseThrow(),null)));
    }
    @PostMapping("/create_quizzes")
    public ResponseEntity<?> createQuiz(@RequestBody List<QuizCreateDto> testDto, Principal principal){
        Test test = testRepository.findById(testDto.stream().findAny().orElseThrow().getTest()).orElseThrow();
        return ResponseEntity.ok(quizRepository.saveAll(testDto.stream().map(e->new Quiz(null,test,e.getQuestion(),e.getOptionA(), e.getOptionB(), e.getOptionC(), e.getTrueOption(), e.getPoints())).toList()));
    }

    @PostMapping("/post-lesson")
    public ResponseEntity<?> createLesson(@RequestBody LessonCreateDto lessonCreateDto){
        return ResponseEntity.ok(lessonRepository.save(new Lesson(null, lessonCreateDto.getTitle(), null,null)));
    }
    @DeleteMapping("/delete-lesson/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        lessonRepository.deleteById(id);
        return ResponseEntity.ok("success");
    }
    @PatchMapping("/patch-lesson")
    public ResponseEntity<?> patch(@RequestBody LessonPatch lessonPatch){
        Lesson lesson = lessonRepository.findById(lessonPatch.getId()).orElseThrow();
        if(lessonPatch.getTitle()!=null){
            lesson.setTitle(lesson.getTitle());
        }
        lessonRepository.save(lesson);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/element-{element}")
    public ResponseEntity<?> ad(@PathVariable String element, @RequestBody ElementDto elementDto,Principal principal) {
        if (element.equals("Теорія")) {
            theoryRepository.save(new Theory(null, elementDto.getTitle(), lessonRepository.findById(elementDto.getParentId()).orElseThrow(), null, principal.getName()));
        } else {
            testRepository.save(new Test(null, elementDto.getTitle(),null,null, principal.getName(), lessonRepository.findById(elementDto.getParentId()).orElseThrow(),null));
        }
        return ResponseEntity.ok("success");
    }
}
