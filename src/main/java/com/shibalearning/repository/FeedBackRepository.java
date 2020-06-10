package com.shibalearning.repository;

import com.shibalearning.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    List<FeedBack> findAll();
    Page<FeedBack> findAll(Pageable p);
    Page<FeedBack> findAllByUser_Id(Pageable p, Long studentId);
    Page<FeedBack> findAllByCourse_Id(Pageable p, Long courseId);
    Page<FeedBack> findAllByCourse_IdAndUser_Id(Pageable p, Long courseId, Long studentId);
    FeedBack findFirstByCourse_IdAndUser_Id(Long courseId, Long studentId);
    FeedBack findById(long id);
}
