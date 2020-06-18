package com.shibalearning.entity;

import com.shibalearning.input.create.SubjectInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "subject")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject extends BaseEntity {

    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "gradeId")
    private Grade grade;
    @Column
    private String image;

    public Subject (SubjectInput subjectInput){
        this.name = subjectInput.getName();
        this.description = subjectInput.getDescription();
    }

}
