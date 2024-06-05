package com.acheron.megalaba.lessons.repository;

import com.acheron.megalaba.lessons.entity.Lesson;
import com.acheron.megalaba.lessons.entity.UserTest;
import com.acheron.megalaba.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {

    List<UserTest> findAllByUser(User user);
}
