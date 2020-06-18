package com.shibalearning.service;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.FeedBack;
import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.FeedBackInput;
import com.shibalearning.input.update.FeedBackUpdateInput;
import com.shibalearning.repository.CourseRepository;
import com.shibalearning.repository.FeedBackRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseService courseService;

    public FeedBack create(FeedBackInput feedBackInput) throws SystemException {
        Course course = courseRepository.findById(feedBackInput.getCourseId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        User student = userRepository.findById(feedBackInput.getStudentId());
        if (student == null || student.getRole().getId() == 1 )
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
//        if (feedBackRepository.findFirstByCourse_IdAndUser_Id(feedBackInput.getCourseId(), feedBackInput.getStudentId()) != null)
//            throw new SystemException(ExceptionCode.FEEDBACK_EXISTED);
        if (feedBackInput.getRate() > 10.00)
            throw new SystemException(ExceptionCode.RATE_INVALID);
        FeedBack feedBack = new FeedBack(feedBackInput);
        feedBack.setCourse(course);
        feedBack.setUser(student);
        return feedBackRepository.save(feedBack);
    }

    public FeedBack update(FeedBackUpdateInput feedBackUpdateInput) throws SystemException {
        FeedBack feedBackUpdated = feedBackRepository.findById(feedBackUpdateInput.getId());
        if (feedBackUpdated == null)
            throw new SystemException(ExceptionCode.FEED_BACK_NOT_FOUND);
        if (feedBackUpdateInput.getNewCourseId() > 0 ){
            Course newCourse = courseRepository.findById(feedBackUpdateInput.getNewCourseId());
            if (newCourse == null)
                throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
            else
                feedBackUpdated.setCourse(newCourse);
        }
        if (feedBackUpdateInput.getNewStudentId() > 0 ){
            User newStudent = userRepository.findById(feedBackUpdateInput.getNewStudentId());
            if (newStudent == null || newStudent.getRole().getId() == 1 )
                throw new SystemException(ExceptionCode.USER_NOT_FOUND);
            else
                feedBackUpdated.setUser(newStudent);
        }
        if (feedBackUpdateInput.getNewRate() > 10.00)
            throw new SystemException(ExceptionCode.RATE_INVALID);
        if (feedBackUpdateInput.getNewRate() > 0 )
            feedBackUpdated.setRate(feedBackUpdateInput.getNewRate());
        if (feedBackUpdateInput.getNewComment() != null)
            feedBackUpdated.setComment(feedBackUpdateInput.getNewComment());
        return feedBackRepository.save(feedBackUpdated);
    }

    public Page<FeedBack> search(int page, int size, Long courseId, Long studentId){
        Pageable pageable = PageRequest.of(page, size);
        if (courseId != null) {
            if (studentId == null)
                return feedBackRepository.findAllByCourse_Id(pageable, courseId);
            else
                return feedBackRepository.findAllByCourse_IdAndUser_Id(pageable, courseId, studentId);
        } else if (studentId != null)
            return feedBackRepository.findAllByUser_Id(pageable, studentId);
        return feedBackRepository.findAll(pageable);
    }

    public FeedBack getById(long id) throws SystemException {
        FeedBack feedBack = feedBackRepository.findById(id);
        if (feedBack == null)
            throw new SystemException(ExceptionCode.FEED_BACK_NOT_FOUND);
        return feedBack;
    }

    public void deleteById(long id) throws SystemException {
        try {
            feedBackRepository.deleteById((Long) id);
        }catch (EmptyResultDataAccessException e){
            throw new SystemException(ExceptionCode.FEED_BACK_NOT_FOUND);
        }
    }

    @Async
    public void updateRateCourse(long courseId){
        List<FeedBack> feedBacks = feedBackRepository.findAllByCourse_Id(courseId);
        courseService.updateRate(feedBacks);
    }
}
