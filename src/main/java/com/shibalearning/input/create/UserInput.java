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
public class UserInput {

    private String userName;
    private String password;
    private String email;
    private MultipartFile avatar;
    private Integer type;

}
