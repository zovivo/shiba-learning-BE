package com.shibalearning.input.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonInput {

    private long courseId;
    private String title;
    private String description;
    private String document;
    private String video;

}
