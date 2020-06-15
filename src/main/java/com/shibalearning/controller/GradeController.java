package com.shibalearning.controller;

import com.shibalearning.entity.Grade;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.GradeInput;
import com.shibalearning.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grade/")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping("create")
    public ResponseData create(@RequestBody GradeInput gradeInput) {
        Grade grade = gradeService.create(gradeInput);
        if (grade == null)
            return new ResponseData(Status.FAIL, ExceptionCode.FAIL, "Grade already exists\n", null);
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Create a new grade successfully\n", grade);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size) {
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS, "Success", gradeService.search(page, size));
    }

}
