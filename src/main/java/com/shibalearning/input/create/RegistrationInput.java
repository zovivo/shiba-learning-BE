package com.shibalearning.input.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInput {
    private long studentId;
    private long courseId;
    private String studentEmail;
}
