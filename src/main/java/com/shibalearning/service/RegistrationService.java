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

import java.util.List;

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

    public Page<Registration> search(int page, int size, Long courseId, Long studentId, Boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        if (courseId != null) {
            if (studentId == null)
                if (active != null)
                    return registrationRepository.findAllByCourse_IdAndActiveEquals(pageable, courseId, active);
                else
                    return registrationRepository.findAllByCourse_Id(pageable, courseId);
            else if (active != null)
                return registrationRepository.findAllByUser_IdAndCourse_IdAndActiveEquals(pageable, studentId, courseId, active);
            else
                return registrationRepository.findAllByUser_IdAndCourse_Id(pageable, studentId, courseId);

        } else if (studentId != null)
            if (active != null)
                return registrationRepository.findAllByUser_IdAndActiveEquals(pageable, studentId, active);
            else
                return registrationRepository.findAllByUser_Id(pageable, studentId);
        else if (active != null)
            return registrationRepository.findAllByActiveEquals(pageable,active);
        return registrationRepository.findAll(pageable);
    }

    public Page<Registration> searchWithUserNameStudent(int page, int size, Long courseId, String userNameStudent, Boolean active) throws SystemException {
        User student = userRepository.findFirstByUserName(userNameStudent);
        if (student == null || student.getRole().getId() != 2)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        return search(page, size, courseId, student.getId(), active);
    }

    public Page<Registration> searchByUserNameStudent(int page, int size, String userNameStudent){
        Pageable pageable = PageRequest.of(page, size);
        return registrationRepository.findAllByUser_UserNameContaining(pageable,userNameStudent);
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

    public Registration activeById(long id) throws SystemException {
        Registration registration = registrationRepository.findById(id);
        if (registration == null)
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        registration.setActive(true);
        return registrationRepository.save(registration);
    }

    public Registration deactivateById(long id) throws SystemException {
        Registration registration = registrationRepository.findById(id);
        if (registration == null)
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        registration.setActive(false);
        return registrationRepository.save(registration);
    }

    public Registration addStudentToCourse(RegistrationInput registrationInput) throws SystemException {
        Course course = courseRepository.findById(registrationInput.getCourseId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        User student = userRepository.findFirstByEmail(registrationInput.getStudentEmail());
        if (student == null || student.getRole().getId() == 1)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        if ( registrationRepository.findFirstByUser_IdAndCourse_Id(student.getId(), registrationInput.getCourseId()) != null)
            throw new SystemException(ExceptionCode.REGISTRATION_EXISTED);
        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setUser(student);
        return registrationRepository.save(registration);
    }

    public Registration addPoint(RegistrationInput registrationInput) throws SystemException {
        Course course = courseRepository.findById(registrationInput.getCourseId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        User student = userRepository.findById(registrationInput.getStudentId());
        if (student == null || student.getRole().getId() == 1)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        Registration registration = registrationRepository.findFirstByUser_IdAndCourse_Id(student.getId(), registrationInput.getCourseId());
        if (registration == null)
            throw new SystemException(ExceptionCode.REGISTRATION_NOT_FOUND);
        registration.setPoint((int) registrationInput.getPoint());
        return registrationRepository.save(registration);
    }

    public Page<Registration> searchByTeacher(int page, int size, long teacherId, String studentName) throws SystemException {
        Pageable pageable = PageRequest.of(page, size);
        User teacher = userRepository.findById(teacherId);
        if (teacher == null || teacher.getRole().getId() != 1)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        List<Course> courses = courseRepository.findAllByTeacher_Id(teacherId);
        if (courses == null || courses.isEmpty())
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        if (studentName != null)
            return registrationRepository.findAllByCourseInAndUser_UserNameContaining(pageable, courses, studentName);
        return registrationRepository.findAllByCourseIn(pageable, courses);
    }
}
