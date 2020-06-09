package com.shibalearning.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUpdateInput {
    private long id;
    private long newCourseId;
    private long newStudentId;
}
