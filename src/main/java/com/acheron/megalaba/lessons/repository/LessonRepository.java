package com.acheron.megalaba.lessons.repository;

import com.acheron.megalaba.lessons.entity.Lesson;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

//    List<Lesson> findAll(Pageable pageable);
}
