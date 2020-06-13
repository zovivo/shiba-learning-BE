package com.shibalearning.entity;

import com.shibalearning.input.create.LessonInput;
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
@Entity(name = "lesson")
public class Lesson extends BaseEntity {

    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private String document;
    @Column
    private String video;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    public Lesson (LessonInput lessonInput){
        this.title = lessonInput.getTitle();
        this.description = lessonInput.getDescription();
        this.document = lessonInput.getDocument();
        this.video = lessonInput.getVideo();
    }

}
