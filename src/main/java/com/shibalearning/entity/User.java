package com.shibalearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shibalearning.input.create.UserInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User extends BaseEntity {

    @Column
    private String userName;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name = "roleId")
    @RestResource(path = "role", rel="role")
    private Role role;
    @Column
    private String avatar;

    public User (UserInput userInput){
        this.userName = userInput.getUserName();
        this.password = userInput.getPassword();
        this.email = userInput.getEmail();
    }
}
