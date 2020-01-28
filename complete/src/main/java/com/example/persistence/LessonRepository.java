package com.example.persistence;

import java.util.List;

import com.example.domain.Lesson;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {

    @Override
    List<Lesson> findAll();

}
