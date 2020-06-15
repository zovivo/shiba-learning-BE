package com.shibalearning.controller;

import com.shibalearning.entity.Lesson;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.LessonInput;
import com.shibalearning.input.update.LessonUpdateInput;
import com.shibalearning.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson/")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("create")
    public ResponseData create(@ModelAttribute LessonInput lessonInput) throws SystemException {
        Lesson lesson = null;
        try {
            lesson = lessonService.create(lessonInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Create a new lesson successfully", lesson);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String title, @RequestParam(required = false) Long courseId) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", lessonService.search(page, size, title, courseId));
    }

    @PostMapping("update")
    public ResponseData update(@ModelAttribute LessonUpdateInput lessonUpdateInput) {
        Lesson lessonUpdated = null;
        try {
            lessonUpdated = lessonService.update(lessonUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.LESSON_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.LESSON_NOT_FOUND, "No lesson found", null);
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Updated lesson Successfully", lessonUpdated);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody LessonUpdateInput lessonUpdateInput) {
        try {
            lessonService.deleteById(lessonUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.LESSON_NOT_FOUND, "No lesson found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Successfully deleted the lesson", "ID: " + lessonUpdateInput.getId());
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        Lesson lesson = null;
        try {
            lesson = lessonService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.LESSON_NOT_FOUND, "No lesson found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", lesson);
    }
}
