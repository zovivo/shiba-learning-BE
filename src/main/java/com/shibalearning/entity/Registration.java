package com.shibalearning.entity;

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
@Entity(name = "registration")
public class Registration extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private User user;
    @Column(columnDefinition = "boolean default false")
    private boolean active;
    @Column
    private Double point;

}
