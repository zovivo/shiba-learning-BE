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
public class UserUpdateInput {

    private String userName;
    private String password;
    private String newPassword;
    private String newEmail;
    private MultipartFile newAvatar;
}
