package com.shibalearning.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackUpdateInput {

    private long id;
    private long newStudentId;
    private long newCourseId;
    private Integer newRate;
    private String newComment;
}
