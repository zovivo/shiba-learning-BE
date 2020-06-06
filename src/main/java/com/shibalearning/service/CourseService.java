package com.shibalearning.service;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.Subject;
import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.CourseInput;
import com.shibalearning.repository.CourseRepository;
import com.shibalearning.repository.SubjectRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public Course create(CourseInput courseInput) throws SystemException {
        Subject subject = subjectRepository.findById(courseInput.getSubjectId());
        if (subject == null)
            throw new SystemException(ExceptionCode.SUBJECT_NOT_FOUND);
        User teacher = userRepository.findById(courseInput.getTeacherId());
        if (teacher == null || teacher.getRole().getId() == 1 )
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        Course course = new Course(courseInput);
        course.setSubject(subject);
        course.setTeacher(teacher);
        if (courseInput.getImage() != null)
            course.setImage(cloudinaryService.uploadFile(courseInput.getImage()));
        return courseRepository.save(course);
    }

    public Page<Course> search(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }
}
