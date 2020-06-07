package com.shibalearning.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateInput {

    private long id;
    private String newName;
    private String newDescription;
    private MultipartFile newImage;
    private MultipartFile newCover;
    private long newTeacherId;
    private long newSubjectId;

}
