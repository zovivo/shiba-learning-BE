package com.shibalearning.service;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.Lesson;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.LessonInput;
import com.shibalearning.input.update.LessonUpdateInput;
import com.shibalearning.repository.CourseRepository;
import com.shibalearning.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Lesson create(LessonInput lessonInput) throws SystemException {
        Course course = courseRepository.findById(lessonInput.getCourseId());
        if (course == null)
            throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
        Lesson lesson = new Lesson(lessonInput);
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    public Lesson update(LessonUpdateInput lessonUpdateInput) throws SystemException {
        Lesson lesson = lessonRepository.findById(lessonUpdateInput.getId());
        if (lesson == null)
            throw new SystemException(ExceptionCode.LESSON_NOT_FOUND);
        if (lessonUpdateInput.getNewCourseId() > 0) {
            Course newCourse = courseRepository.findById(lessonUpdateInput.getNewCourseId());
            if (newCourse == null)
                throw new SystemException(ExceptionCode.COURSE_NOT_FOUND);
            else
                lesson.setCourse(newCourse);
        }
        if (lessonUpdateInput.getNewTitle() != null && !lessonUpdateInput.getNewTitle().isEmpty())
            lesson.setTitle(lessonUpdateInput.getNewTitle());
        if (lessonUpdateInput.getNewDescription() != null && !lessonUpdateInput.getNewDescription().isEmpty())
            lesson.setDescription(lessonUpdateInput.getNewDescription());
        if (lessonUpdateInput.getNewDocument() != null && !lessonUpdateInput.getNewDocument().isEmpty())
            lesson.setDocument(lessonUpdateInput.getNewDocument());
        if (lessonUpdateInput.getNewVideo() != null && !lessonUpdateInput.getNewVideo().isEmpty())
            lesson.setVideo(lessonUpdateInput.getNewVideo());
        return lessonRepository.save(lesson);
    }

    public Page<Lesson> search(int page, int size, String title, Long courseId){
        Pageable pageable = PageRequest.of(page, size);
        if (title == null)
            title = "";
        if (courseId != null)
            return lessonRepository.findAllByTitleContainingAndCourse_Id(pageable, title, courseId);
        return lessonRepository.findAllByTitleContaining(pageable, title);
    }

    public Lesson getById(long id) throws SystemException {
        Lesson lesson = lessonRepository.findById(id);
        if (lesson == null)
            throw new SystemException(ExceptionCode.LESSON_NOT_FOUND);
        return lesson;
    }

    public void deleteById(long id) throws SystemException {
        try {
            lessonRepository.deleteById((Long) id);
        }catch (EmptyResultDataAccessException e){
            throw new SystemException(ExceptionCode.LESSON_NOT_FOUND);
        }
    }

}
