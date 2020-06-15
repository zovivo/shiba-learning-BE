package com.shibalearning.controller;

import com.shibalearning.entity.Subject;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.SubjectInput;
import com.shibalearning.input.update.SubjectUpdateInput;
import com.shibalearning.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject/")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("create")
    public ResponseData create(@RequestBody SubjectInput subjectInput) throws SystemException {
        Subject subject = null;
        try {
            subject = subjectService.create(subjectInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.GRADE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.GRADE_NOT_FOUND, "No grade found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Create a new subject successfully", subject);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name, @RequestParam(required = false) Long grade) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", subjectService.search(page, size, name, grade));
    }

    @GetMapping("get-by-id")
    public ResponseData getById(@RequestParam long id) {
        Subject subject = null;
        try {
            subject = subjectService.getById(id);
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.SUBJECT_NOT_FOUND, "No subject found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", subject);
    }

    @PostMapping("delete-by-id")
    public ResponseData deleteById(@RequestBody SubjectUpdateInput subjectUpdateInput) {
        try {
            subjectService.deleteById(subjectUpdateInput.getId());
        } catch (SystemException e) {
            return new ResponseData(Status.FAIL, ExceptionCode.SUBJECT_NOT_FOUND, "No subject found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Successfully deleted the subject", "ID: " + subjectUpdateInput.getId());
    }

    @PostMapping("update")
    public ResponseData update(@RequestBody SubjectUpdateInput subjectUpdateInput) {
        Subject subject = null;
        try {
            subject = subjectService.update(subjectUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.GRADE_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.GRADE_NOT_FOUND, "No grade found", null);
            if (e.getExceptionCode() == ExceptionCode.SUBJECT_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.SUBJECT_NOT_FOUND, "No subject found", null);
        }
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Update subject successfully", subject);
    }

}
