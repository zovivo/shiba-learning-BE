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
import org.springframework.data.domain.Page;
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
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
            if (e.getExceptionCode() == ExceptionCode.REGISTRATION_EXISTED)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_EXISTED, "Students have already signed up for this course", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Sign up for the course successfully", registration);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Long studentId, @RequestParam(required = false) Long courseId, @RequestParam(required = false) Boolean active, @RequestParam(required = false) String userName) {
        if (userName != null){
            try {
                Page<Registration> registrationPage = registrationService.searchWithUserNameStudent(page, size, courseId, userName, active);
                return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", registrationPage);
            } catch (SystemException e) {
                if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                    return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
            }
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", registrationService.search(page, size, courseId, studentId, active));
    }

    @PostMapping("update")
    public ResponseData update(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        Registration registrationUpdated = null;
        try {
            registrationUpdated = registrationService.update(registrationUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.REGISTRATION_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Course registration not found", null);
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Update registration for the Course Successfully", registrationUpdated);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        try {
            registrationService.deleteById(registrationUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Course registration not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Successfully deleted course registration", "ID: " + registrationUpdateInput.getId());
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        Registration registration = null;
        try {
            registration = registrationService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Course registration not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", registration);
    }

    @PostMapping("active-by-id")
    public ResponseData activeById(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        Registration registration = null;
        try {
            registration = registrationService.activeById(registrationUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Course registration not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Successful activation", registration);
    }

    @PostMapping("inactive-by-id")
    public ResponseData deactivateById(@RequestBody RegistrationUpdateInput registrationUpdateInput) {
        Registration registration = null;
        try {
            registration = registrationService.deactivateById(registrationUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_NOT_FOUND, "Course registration not found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Deactivated successfully", registration);
    }

    @PostMapping("add-student-to-course")
    public ResponseData addStudentToCourse(@RequestBody RegistrationInput registrationInput) {
        Registration registration = null;
        try {
            registration = registrationService.addStudentToCourse(registrationInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.COURSE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.COURSE_NOT_FOUND, "No courses found", null);
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND, "No student found", null);
            if (e.getExceptionCode() == ExceptionCode.REGISTRATION_EXISTED)
                return new ResponseData(Status.FAIL, ExceptionCode.REGISTRATION_EXISTED, "Students have already signed up for this course", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Sign up for the course successfully", registration);
    }
}
