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
public class SubjectInput {

    private String name;
    private long grade;
    private String description;
    private MultipartFile image;
}
