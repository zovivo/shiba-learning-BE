package com.shibalearning.repository;

import com.shibalearning.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findAll();
    Page<Subject> findAll(Pageable p);
    Subject findById(long id);
    Page<Subject> findAllByNameContaining(Pageable p, String name);
    Page<Subject> findAllByNameContainingAndGrade_Id(Pageable p, String name, Long grade);
    int deleteById(long id);
}
