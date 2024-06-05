package com.acheron.megalaba.lessons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ToString.Exclude
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.ALL)
    private List<Theory> theories;
    @ToString.Exclude
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.ALL)
    private List<Test> tests;

}
