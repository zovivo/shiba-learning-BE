package com.shibalearning.entity;

import com.shibalearning.input.create.GradeInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "grade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Grade extends BaseEntity {

    private String name;

    public Grade (GradeInput gradeInput){
        this.name = gradeInput.getName();
    }

}
