package com.shibalearning.controller;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.CourseInput;
import com.shibalearning.input.update.CourseUpdateInput;
import com.shibalearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("create")
    public ResponseData create(@ModelAttribute CourseInput courseInput) throws SystemException {
        Course course = null;
        try {
            course = courseService.create(courseInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.SUBJECT_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.SUBJECT_NOT_FOUND, "No subject found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No teacher found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Create a new course successfully", course);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name, @RequestParam(required = false) Long subjectId) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", courseService.search(page, size, name, subjectId));
    }

    @GetMapping("get-all")
    public ResponseData getAll(@RequestParam(required = false) Long teacherId) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", courseService.getByTeacher(teacherId));
    }

    @PostMapping("update")
    public ResponseData update(@ModelAttribute CourseUpdateInput courseUpdateInput) {
        Course courseUpdated = null;
        try {
            courseUpdated = courseService.update(courseUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.SUBJECT_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.SUBJECT_NOT_FOUND, "No subject found", null);
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No teacher found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Course update Successfully", courseUpdated);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody CourseUpdateInput courseUpdateInput) {
        try {
            courseService.deleteById(courseUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "Course not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Course successfully deleted", "ID: " + courseUpdateInput.getId());
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        Course course = null;
        try {
            course = courseService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "Course not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", course);
    }
}
