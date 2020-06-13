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
public class LessonUpdateInput {
    
    private long id;
    private long newCourseId;
    private String newTitle;
    private MultipartFile newImage;
    private String newDescription;
    private String newDocument;
    private String newVideo;
}
