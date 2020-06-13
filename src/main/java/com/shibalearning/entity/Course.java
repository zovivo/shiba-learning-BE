package com.shibalearning.entity;

import com.shibalearning.input.create.CourseInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "course")
public class Course extends BaseEntity {

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private String cover;
    @ManyToOne
    @JoinColumn(name = "teacherId")
    private User teacher;
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;
    @Column
    private Double rate;

    public Course(CourseInput courseInput){
        this.name = courseInput.getName();
        this.description = courseInput.getDescription();
    }

}
