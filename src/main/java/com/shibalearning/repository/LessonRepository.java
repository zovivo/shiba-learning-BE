package com.shibalearning.repository;

import com.shibalearning.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAll();

    Page<Lesson> findAll(Pageable p);

    Page<Lesson> findAllByTitleContainingAndCourse_Id(Pageable p, String title, Long courseId);

    Page<Lesson> findAllByTitleContaining(Pageable p, String title);

    Lesson findById(long id);
}
