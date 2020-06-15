package com.shibalearning.controller;

import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.entity.response.ResponseData;
import com.shibalearning.input.create.UserInput;
import com.shibalearning.input.create.UserLoginInput;
import com.shibalearning.input.update.UserUpdateInput;
import com.shibalearning.service.CloudinaryService;
import com.shibalearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("all")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("login")
    public ResponseData login(@RequestBody UserLoginInput userLoginInput){
        User user = userService.login(userLoginInput);
        if (user == null)
            return new ResponseData(Status.FAIL, ExceptionCode.FAIL,"The username or password is incorrect",null);
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS,"Logged in successfully",user);
    }

    @PostMapping("create")
    public ResponseData create(@ModelAttribute UserInput userInput){
        User user = null;
        try {
            user = userService.create(userInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.USER_NAME_EXIST) {
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NAME_EXIST,"userName already exists",null);
            }
            if (e.getExceptionCode() == ExceptionCode.EMAIL_EXIST) {
                return new ResponseData(Status.FAIL, ExceptionCode.EMAIL_EXIST,"Email already exists",null);
            }
        }
        return new ResponseData(Status.SUCCESS,ExceptionCode.SUCCESS ,"Account successfully created",user);
    }

    @PostMapping("update")
    public ResponseData update(@ModelAttribute UserUpdateInput userUpdateInput){
        User user = null;
        try {
            user = userService.updateProfile(userUpdateInput);
        } catch (SystemException e) {
            if (e.getExceptionCode() == ExceptionCode.USER_NOT_FOUND)
                return new ResponseData(Status.FAIL, ExceptionCode.USER_NOT_FOUND,"No user found",null);
        }
        return new ResponseData(Status.SUCCESS,ExceptionCode.SUCCESS,"Update account information successfully",user);
    }

    @PostMapping("change-password")
    public ResponseData changePassword(@RequestBody UserUpdateInput userUpdateInput){
        User user = userService.changePassword(userUpdateInput);
        if (user == null)
            return new ResponseData(Status.FAIL,ExceptionCode.USER_NOT_FOUND ,"No user found or wrong password",null);
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS,"Change password successfully",user);
    }

    @PostMapping("upload")
    public String uploadFile(@ModelAttribute(value = "image") MultipartFile image){
        return cloudinaryService.uploadFile(image);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String userName,  @RequestParam(required = false) String email, @RequestParam(required = false) String sortBy){
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS,"Success",userService.search(page, size,userName,email,sortBy));
    }

    @GetMapping("get-by-username")
    public ResponseData search(@RequestParam String userName){
        User user = userService.getByUserName(userName);
        if (user == null)
            return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS,"No user found",user);
        return new ResponseData(Status.SUCCESS, ExceptionCode.SUCCESS,"Success",user);
    }
}
