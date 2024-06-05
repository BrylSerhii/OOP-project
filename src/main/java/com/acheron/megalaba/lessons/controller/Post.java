package com.acheron.megalaba.lessons.controller;

import com.acheron.megalaba.lessons.dto.PatchElementTitleType;
import com.acheron.megalaba.lessons.dto.PatchTheoryType;
import com.acheron.megalaba.lessons.dto.PostElementDataType;
import com.acheron.megalaba.lessons.dto.PostQuizType;
import com.acheron.megalaba.lessons.entity.Lesson;
import com.acheron.megalaba.lessons.entity.Quiz;
import com.acheron.megalaba.lessons.entity.Test;
import com.acheron.megalaba.lessons.entity.Theory;
import com.acheron.megalaba.lessons.repository.LessonRepository;
import com.acheron.megalaba.lessons.repository.QuizRepository;
import com.acheron.megalaba.lessons.repository.TestRepository;
import com.acheron.megalaba.lessons.repository.TheoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class Post {
    private final QuizRepository quizRepository;
    private final TestRepository testRepository;
    private final TheoryRepository theoryRepository;
    private final LessonRepository lessonRepository;

    @PostMapping("/post-quiz")
    public String ad(@RequestBody PostQuizType postQuizType){
        Test test = testRepository.findById(postQuizType.getParentTestId()).get();
        test.setPoints((test.getPoints()!=null?test.getPoints():0)+ postQuizType.getPoints());
        testRepository.save(test);

        quizRepository.save(new Quiz(null,test, postQuizType.getQuestion(), postQuizType.getOptionA(), postQuizType.getOptionB(), postQuizType.getOptionC(), postQuizType.getCorrectOption(), postQuizType.getPoints() ));
        return "success";
    }

    @PatchMapping("/patch-theory/{id}")
    public String asd(@PathVariable Long id, @RequestBody PatchTheoryType patchTheoryType){
        Theory theory = theoryRepository.findById(id).orElseThrow();
        if (patchTheoryType.getHtml()!=null){
            theory.setHtml(patchTheoryType.getHtml());
        }
        theoryRepository.save(theory);
        return "success";
    }
    @PatchMapping("/patch-title-{element}/{id}")
    public String asdfd(@PathVariable String element, @PathVariable Long id, @RequestBody PatchElementTitleType patchElementTitleType){
        if(element.equals("lesson")){
            Lesson lesson = lessonRepository.findById(id).orElseThrow();
            lesson.setTitle(patchElementTitleType.getTitle());
                lessonRepository.save(lesson);
                return "lessons";
        }if(element.equals("test")){
            var lesson = testRepository.findById(id).orElseThrow();
            lesson.setTitle(patchElementTitleType.getTitle());
            testRepository.save(lesson);
            return element+"/0"+id.toString();
        }if(element.equals("theory")){
            var lesson = theoryRepository.findById(id).orElseThrow();
            lesson.setTitle(patchElementTitleType.getTitle());
            theoryRepository.save(lesson);
            return element+"/0"+id.toString();
        }
        return "success";
    }
    @PostMapping("/post-{element}")
    public String sadasd(@PathVariable String element, @RequestBody PostElementDataType postElementDataType, Principal principal){
        if(element.equals("lesson")){
            lessonRepository.save(new Lesson(null, postElementDataType.getTitle(), null,null));
        }if(element.equals("test")){
           testRepository.save(new Test(null, postElementDataType.getTitle(), null,null,principal.getName(),lessonRepository.findById(postElementDataType.getParentId()).orElseThrow(),null));
        }if(element.equals("theory")){
           theoryRepository.save(new Theory(null, postElementDataType.getTitle(), lessonRepository.findById(postElementDataType.getParentId()).orElseThrow(),null,principal.getName()));
        }
        return "success";
    }
    @DeleteMapping("/delete-{element}/{id}")
    public String sadas(@PathVariable String element, @PathVariable Long id){
        if(element.equals("lesson")){
            lessonRepository.deleteById(id);
            return "lessons";
        }if(element.equals("test")){
            testRepository.deleteById(id);
            return element+"/0"+id.toString();
        }if(element.equals("theory")){
            theoryRepository.deleteById(id);
            return element+"/0"+id.toString();
        }if(element.equals("quiz")){
            quizRepository.deleteById(id);
            return element+"/0"+id.toString();
        }
        return "success";
    }
}
