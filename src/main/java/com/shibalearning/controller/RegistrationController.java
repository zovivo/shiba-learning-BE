package com.shibalearning.controller;

import com.shibalearning.entity.Registration;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.RegistrationInput;
import com.shibalearning.input.update.RegistrationUpdateInput;
import com.shibalearning.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping("create")
    public ResponseData create(@RequestBody RegistrationInput registrationInput) {
        Registration registration = null;
        try {
            registration = registrationService.create(registrationInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "Không tìm thấy học sinh", null);
            if (e.getExceptionCode() == ExceptionCode.REGISTRATION_EXISTED)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_EXISTED, "Học sinh đã đăng ký khóa học này rồi", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Đăng ký khoá học thành công", registration);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Long studentId, @RequestParam(required = false) Long courseId) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Thành công", registrationService.search(page, size, courseId, studentId));
    }

    @PostMapping("update")
    public ResponseData update(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        Registration registrationUpdated = null;
        try {
            registrationUpdated = registrationService.update(registrationUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.REGISTRATION_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Không tìm thấy đăng ký khóa học", null);
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "Không tìm thấy học sinh", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Cập nhật đăng ký khóa học Thành công", registrationUpdated);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        try {
            registrationService.deleteById(registrationUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Không tìm thấy đăng ký khoá học", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Xóa đăng ký khóa học thành công", "ID: " + registrationUpdateInput.getId());
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        Registration registration = null;
        try {
            registration = registrationService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Không tìm thấy đăng ký khoá học", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Thành công", registration);
    }
}
