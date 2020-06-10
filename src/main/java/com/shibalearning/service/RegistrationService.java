package com.shibalearning.service;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.Registration;
import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.RegistrationInput;
import com.shibalearning.input.update.RegistrationUpdateInput;
import com.shibalearning.repository.CourseRepository;
import com.shibalearning.repository.RegistrationRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public Registration create(RegistrationInput registrationInput) throws SystemException {
        Course course = courseRepository.findById(registrationInput.getCourseId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        User student = userRepository.findById(registrationInput.getStudentId());
        if (student == null || student.getRole().getId() == 1)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        if ( registrationRepository.findFirstByUser_IdAndCourse_Id(registrationInput.getStudentId(), registrationInput.getCourseId()) != null)
            throw new SystemException(ExceptionCode.REGISTRATION_EXISTED);
        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setUser(student);
        return registrationRepository.save(registration);
    }

    public Registration update(RegistrationUpdateInput registrationUpdateInput) throws SystemException {
        Registration registration = registrationRepository.findById(registrationUpdateInput.getId());
        if (registration == null)
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        if (registrationUpdateInput.getNewCourseId() > 0) {
            Course newCourse = courseRepository.findById(registrationUpdateInput.getNewCourseId());
            if (newCourse == null)
                throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
            else
                registration.setCourse(newCourse);
        }
        if (registrationUpdateInput.getNewStudentId() > 0) {
            User newStudent = userRepository.findById(registrationUpdateInput.getNewStudentId());
            if (newStudent == null || newStudent.getRole().getId() == 1)
                throw new SystemException(ExceptionCode.USER_NOT_FOUND);
            else
                registration.setUser(newStudent);
        }
        return registrationRepository.save(registration);
    }

    public Page<Registration> search(int page, int size, Long courseId, Long studentId) {
        Pageable pageable = PageRequest.of(page, size);
        if (courseId != null) {
            if (studentId == null)
                return registrationRepository.findAllByCourse_Id(pageable, courseId);
            else
                return registrationRepository.findAllByUser_IdAndCourse_Id(pageable, studentId, courseId);
        } else if (studentId != null)
            return registrationRepository.findAllByUser_Id(pageable, studentId);
        return registrationRepository.findAll(pageable);
    }

    public Registration getById(long id) throws SystemException {
        Registration registration = registrationRepository.findById(id);
        if (registration == null)
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        return registration;
    }

    public void deleteById(long id) throws SystemException {
        try {
            registrationRepository.deleteById((Long) id);
        } catch (EmptyResultDataAccessException e) {
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        }
    }
}
