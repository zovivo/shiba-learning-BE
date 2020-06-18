package com.shibalearning.controller;

import com.shibalearning.entity.Role;
import com.shibalearning.input.create.RoleInput;
import com.shibalearning.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "create")
    public Role createRole(@RequestBody RoleInput roleInput){
        return  roleService.create(roleInput);
    }
    @GetMapping(value = "get-all")
    public List<Role> getAllRole(){
        return  roleService.getAll();
    }
}
