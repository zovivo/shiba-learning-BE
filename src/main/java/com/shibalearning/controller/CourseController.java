package com.shibalearning.controller;

import com.shibalearning.entity.Course;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.CourseInput;
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
        }catch (SystemException e){
            if (e.getExceptionCode() == ExceptionCode.SUBJECT_NOT_FOUND)
                return new ResponseData(Status.FAIL,"Không tìm thấy môn học",null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL,"Không tìm thấy giáo viên",null);
        }
        return new ResponseData(Status.SUCCESS,"Tạo khoá học mới thành công",course);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size){
        return  new ResponseData(Status.SUCCESS,"Thành công",courseService.search(page, size));
    }
}
