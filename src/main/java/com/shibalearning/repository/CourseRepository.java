package com.shibalearning.repository;

import com.shibalearning.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAll();
    Page<Course> findAll(Pageable p);
    Page<Course> findAllByNameContains(Pageable p, String name);
    Page<Course> findAllBySubject_IdAndNameContaining(Pageable p, Long subjectId, String name);
    Course findById(long id);
    int deleteById(long id);
}
