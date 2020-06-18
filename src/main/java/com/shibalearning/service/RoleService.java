package com.shibalearning.service;

import com.shibalearning.entity.Role;
import com.shibalearning.input.create.RoleInput;
import com.shibalearning.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role create(RoleInput roleInput){
        Role role = new Role(roleInput);
        return  roleRepository.save(role);
    }

    public List<Role> getAll(){
        return  roleRepository.findAll();
    }
}
