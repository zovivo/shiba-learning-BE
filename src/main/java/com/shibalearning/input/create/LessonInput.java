package com.shibalearning.input.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonInput {

    private long courseId;
    private String title;
    private String description;
    private MultipartFile image;
    private String document;
    private String video;

}
