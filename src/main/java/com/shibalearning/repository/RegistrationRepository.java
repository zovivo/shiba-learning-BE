package com.shibalearning.repository;

import com.shibalearning.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {

    Page<Registration> findAll(Pageable p);
    Page<Registration> findAllByCourse_Id(Pageable p, Long courseId);
    Page<Registration> findAllByUser_Id(Pageable p, Long userId);
    Page<Registration> findAllByUser_IdAndCourse_Id(Pageable p, Long studentId, Long courseId);
    Registration findFirstByUser_IdAndCourse_Id(Long studentId, Long courseId);
    Registration findById(long id);
}
