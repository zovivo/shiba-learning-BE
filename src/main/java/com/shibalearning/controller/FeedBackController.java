package com.shibalearning.controller;

import com.shibalearning.entity.FeedBack;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.FeedBackInput;
import com.shibalearning.input.update.FeedBackUpdateInput;
import com.shibalearning.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback/")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping("create")
    public ResponseData create(@RequestBody FeedBackInput feedBackInput) {
        FeedBack feedBack = null;
        try {
            feedBack = feedBackService.create(feedBackInput);
            feedBackService.updateRateCourse(feedBack.getCourse().getId());
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
//            if (e.getExceptionCode() == ExceptionCode.FEEDBACK_EXISTED)
//                return new ResponseData(Status.FAIL, ExceptionCode.FEEDBACK_EXISTED, "Students have already assessed this course", null);
            if (e.getExceptionCode() == ExceptionCode.RATE_INVALID)
                return new ResponseData(Status.FAIL, ExceptionCode.RATE_INVALID, "Invalid rate", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Create successful course reviews", feedBack);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Long studentId, @RequestParam(required = false) Long courseId) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", feedBackService.search(page, size, courseId, studentId));
    }

    @PostMapping("update")
    public ResponseData update(@RequestBody FeedBackUpdateInput feedBackUpdateInput) {
        FeedBack feedBackUpdated = null;
        try {
            feedBackUpdated = feedBackService.update(feedBackUpdateInput);
            feedBackService.updateRateCourse(feedBackUpdated.getCourse().getId());
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.FEED_BACK_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "No course reviews found", null);
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
            if (e.getExceptionCode() == ExceptionCode.RATE_INVALID)
                return new ResponseData(Status.FAIL, ExceptionCode.RATE_INVALID, "Invalid rate", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Update course evaluation Successfully", feedBackUpdated);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody FeedBackUpdateInput feedBackUpdateInput) {
        try {
            feedBackService.deleteById(feedBackUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.FEED_BACK_NOT_FOUND, "No course reviews found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Successfully deleted the course review", "ID: " + feedBackUpdateInput.getId());
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        FeedBack feedBack = null;
        try {
            feedBack = feedBackService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.FEED_BACK_NOT_FOUND, "No course reviews found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", feedBack);
    }
}
