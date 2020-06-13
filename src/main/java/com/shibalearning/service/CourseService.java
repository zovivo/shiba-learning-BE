package com.shibalearning.service;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.FeedBack;
import com.shibalearning.entity.Subject;
import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.CourseInput;
import com.shibalearning.input.update.CourseUpdateInput;
import com.shibalearning.repository.CourseRepository;
import com.shibalearning.repository.SubjectRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (teacher == null || teacher.getRole().getId() == 2 )
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        Course course = new Course(courseInput);
        course.setSubject(subject);
        course.setTeacher(teacher);
        if (courseInput.getImage() != null && courseInput.getImage().getSize() != 0)
            course.setImage(cloudinaryService.uploadFile(courseInput.getImage()));
        if (courseInput.getCover() != null)
            course.setCover(cloudinaryService.uploadFile(courseInput.getCover()));
        return courseRepository.save(course);
    }

    public Course update(CourseUpdateInput courseUpdateInput) throws SystemException {
        Course course = courseRepository.findById(courseUpdateInput.getId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        if (courseUpdateInput.getNewSubjectId() > 0 ){
            Subject newSubject = subjectRepository.findById(courseUpdateInput.getNewSubjectId());
            if (newSubject == null)
                throw new SystemException(ExceptionCode.SUBJECT_NOT_FOUND);
            else
                course.setSubject(newSubject);
        }
        if (courseUpdateInput.getNewTeacherId() > 0 ){
            User newTeacher = userRepository.findById(courseUpdateInput.getNewTeacherId());
            if (newTeacher == null || newTeacher.getRole().getId() == 2 )
                throw new SystemException(ExceptionCode.USER_NOT_FOUND);
            else
                course.setTeacher(newTeacher);
        }
        if (courseUpdateInput.getNewName() != null && !courseUpdateInput.getNewName().isEmpty())
            course.setName(courseUpdateInput.getNewName());
        if (courseUpdateInput.getNewDescription() != null )
            course.setDescription(courseUpdateInput.getNewDescription());
        if (courseUpdateInput.getNewImage() != null && courseUpdateInput.getNewImage().getSize() != 0)
            course.setImage(cloudinaryService.uploadFile(courseUpdateInput.getNewImage()));
        if (courseUpdateInput.getNewCover() != null)
            course.setCover(cloudinaryService.uploadFile(courseUpdateInput.getNewCover()));
        return courseRepository.save(course);
    }

    public Page<Course> search(int page, int size, String name, Long subjectId){
        Pageable pageable = PageRequest.of(page, size);
        if (name == null)
            name = "";
        if (subjectId != null)
            return courseRepository.findAllBySubject_IdAndNameContaining(pageable, subjectId, name);
        return courseRepository.findAllByNameContains(pageable, name);
    }

    public Course getById(long id) throws SystemException {
        Course course = courseRepository.findById(id);
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        return course;
    }

    public void deleteById(long id) throws SystemException {
        try {
            courseRepository.deleteById((Long) id);
        }catch (EmptyResultDataAccessException e){
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        }
    }

    public Course updateRate(List<FeedBack> feedBacks){
        Course course = courseRepository.findById(feedBacks.get(0).getCourse().getId());
        double sumRate = 0.0;
        for (FeedBack feedBack: feedBacks) {
            sumRate += feedBack.getRate();
        }
        course.setRate((double) Math.round(sumRate/feedBacks.size() * 100) / 100);
        return courseRepository.save(course);
    }
}
