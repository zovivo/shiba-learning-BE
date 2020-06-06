package com.shibalearning.repository;

import com.shibalearning.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findAll();
    Page<Grade> findAll(Pageable p);
    Grade findById(long id);
    Grade findFirstByName(String name);
}
