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
public class SubjectUpdateInput {

    private long id;
    private String newName;
    private String newDescription;
    private long newGrade;
    private MultipartFile newImage;
}
