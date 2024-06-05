package com.acheron.megalaba.lessons.repository;

import com.acheron.megalaba.lessons.entity.Lesson;
import com.acheron.megalaba.lessons.entity.Theory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheoryRepository extends JpaRepository<Theory, Long> {
}
