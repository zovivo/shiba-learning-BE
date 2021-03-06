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
public class CourseInput {
    private String name;
    private String description;
    private MultipartFile image;
    private MultipartFile cover;
    private long teacherId;
    private long subjectId;
}
