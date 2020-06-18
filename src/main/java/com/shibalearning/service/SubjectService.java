package com.shibalearning.service;

import com.shibalearning.entity.Grade;
import com.shibalearning.entity.Subject;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.SubjectInput;
import com.shibalearning.input.update.SubjectUpdateInput;
import com.shibalearning.repository.GradeRepository;
import com.shibalearning.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public Subject create(SubjectInput subjectInput) throws SystemException {
        Grade grade = gradeRepository.findById(subjectInput.getGrade());
        if (grade == null)
            throw new SystemException(ExceptionCode.GRADE_NOT_FOUND);
        Subject subject = new Subject(subjectInput);
        subject.setGrade(grade);
        if (subjectInput.getImage() != null && subjectInput.getImage().getSize() != 0) {
            String image = cloudinaryService.uploadFile(subjectInput.getImage());
            subject.setImage(image);
        }
        return subjectRepository.save(subject);
    }

    public Page<Subject> search(int page, int size, String name, Long grade) {
        Pageable pageable = PageRequest.of(page, size);
        if (name == null)
            name = "";
        if (grade != null)
            return subjectRepository.findAllByNameContainingAndGrade_Id(pageable, name, grade);
        return subjectRepository.findAllByNameContaining(pageable, name);
    }

    public Subject getById(long id) throws SystemException {
        Subject subject = subjectRepository.findById(id);
        if (subject == null)
            throw new SystemException(ExceptionCode.SUBJECT_NOT_FOUND);
        return subject;
    }

    public void deleteById(long id) throws SystemException {
        try {
            subjectRepository.deleteById((Long) id);
        } catch (EmptyResultDataAccessException e) {
            throw new SystemException(ExceptionCode.SUBJECT_NOT_FOUND);
        }
    }

    public Subject update(SubjectUpdateInput subjectUpdateInput) throws SystemException {
        Subject subject = subjectRepository.findById(subjectUpdateInput.getId());
        if (subject == null)
            throw new SystemException(ExceptionCode.SUBJECT_NOT_FOUND);
        if (subjectUpdateInput.getNewName() != null && !subjectUpdateInput.getNewName().isEmpty())
            subject.setName(subjectUpdateInput.getNewName());
        if (subjectUpdateInput.getNewDescription() != null)
            subject.setDescription(subjectUpdateInput.getNewDescription());
        if (subjectUpdateInput.getNewGrade() > 0) {
            Grade grade = gradeRepository.findById(subjectUpdateInput.getId());
            if (grade == null)
                throw new SystemException(ExceptionCode.GRADE_NOT_FOUND);
            subject.setGrade(grade);
        }
        if (subjectUpdateInput.getNewImage() != null && subjectUpdateInput.getNewImage().getSize() != 0) {
            String image = cloudinaryService.uploadFile(subjectUpdateInput.getNewImage());
            subject.setImage(image);
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubject(){
        return subjectRepository.findAll();
    }
}
