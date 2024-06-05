package com.acheron.megalaba.lessons.controller;

import com.acheron.megalaba.lessons.dto.*;
import com.acheron.megalaba.lessons.entity.Lesson;
import com.acheron.megalaba.lessons.entity.Test;
import com.acheron.megalaba.lessons.entity.Theory;
import com.acheron.megalaba.lessons.repository.LessonRepository;
import com.acheron.megalaba.lessons.repository.TestRepository;
import com.acheron.megalaba.lessons.repository.TheoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonRepository lessonRepository;
    private final TheoryRepository theoryRepository;
    private final TestRepository testRepository;

    @GetMapping("/lessons")
    public List<LessonsDto> getLessons() {
        return lessonRepository.findAll().stream().map(e->new LessonsDto(e.getId(),e.getTitle())).toList();
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<?> getLesson(@PathVariable Long id){
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if(lesson != null){
            return ResponseEntity.ok(new LessonDto(lesson.getTheories().stream().map(e-> new TheoryDto(e.getId(),e.getTitle())).toList(),lesson.getTests().stream().map(e->new TestDto(e.getId(), e.getTitle(), e.getQuestionsAmount(), e.getPoints())).toList()));
        }else return ResponseEntity.status(400).body("lesson doesn't exist");
    }

    @GetMapping("/theory/{id}")
    public ResponseEntity<?> getTheory(@PathVariable Long id){
        Theory theory = theoryRepository.findById(id).orElse(null);
        if (theory != null){
            return ResponseEntity.ok(new TheoryGetDto(theory.getId(), theory.getTitle(),theory.getHtml(), theory.getAuthor()));
        }else return ResponseEntity.status(400).body("theory doesn't exist");
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<?> getTest(@PathVariable Long id){
        Test test = testRepository.findById(id).orElse(null);
        if(test != null){
            return ResponseEntity.ok(new TestGetDto(test.getId(), test.getTitle(), test.getPoints(), test.getAuthor(),null,(test.getQuizzes()!=null || !test.getQuizzes().isEmpty())?test.getQuizzes().stream().map(e->new QuizGetDto(e.getId(),e.getQuestion(), e.getOptionA(), e.getOptionB(), e.getOptionC(), e.getPoints())).toList():null));
        }else return ResponseEntity.status(400).body("test doesn't exist");
    }

}
