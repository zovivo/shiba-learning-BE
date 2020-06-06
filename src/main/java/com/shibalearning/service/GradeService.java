package com.shibalearning.service;

import com.shibalearning.entity.Grade;
import com.shibalearning.input.create.GradeInput;
import com.shibalearning.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public Grade create(GradeInput gradeInput){
        if (gradeRepository.findFirstByName(gradeInput.getName()) != null)
            return null;
        Grade grade = new Grade(gradeInput);
        return gradeRepository.save(grade);
    }

    public Page<Grade> search(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gradeRepository.findAll(pageable);
    }
}
