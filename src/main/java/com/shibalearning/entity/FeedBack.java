package com.shibalearning.entity;

import com.shibalearning.input.create.FeedBackInput;
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
@Entity(name = "feedback")
public class FeedBack extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private User user;
    @Column
    private Integer rate;
    @Column
    private String comment;

    public FeedBack (FeedBackInput feedBackInput){
        this.rate = feedBackInput.getRate();
        this.comment = feedBackInput.getComment();
    }

}
