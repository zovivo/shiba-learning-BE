package com.shibalearning.entity;

import com.shibalearning.input.create.RoleInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity {

    @Column
    private String roleName;

    public Role (RoleInput roleInput){
        this.roleName = roleInput.getRoleName();
    }
}
