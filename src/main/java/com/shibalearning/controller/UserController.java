package com.shibalearning.controller;

import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.Status;
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
            return new ResponseData(Status.FAIL,"Tên tài khoản hoặc mật khẩu không đúng",null);
        return new ResponseData(Status.SUCCESS,"Đăng nhập thành công",user);
    }

    @PostMapping("create")
    public ResponseData create(@ModelAttribute UserInput userInput){
        User user = userService.create(userInput);
        if (user == null)
            return new ResponseData(Status.FAIL,"userName đã tồn tại",null);
        return new ResponseData(Status.SUCCESS,"Tạo tài khoản thành công",user);
    }

    @PostMapping("update")
    public ResponseData update(@ModelAttribute UserUpdateInput userUpdateInput){
        User user = userService.updateProfile(userUpdateInput);
        if (user == null)
            return new ResponseData(Status.FAIL,"Không tìm thấy user",null);
        return new ResponseData(Status.SUCCESS,"Cập nhật thông tài khoản thành công",user);
    }

    @PostMapping("change-password")
    public ResponseData changePassword(@RequestBody UserUpdateInput userUpdateInput){
        User user = userService.changePassword(userUpdateInput);
        if (user == null)
            return new ResponseData(Status.FAIL,"Không tìm thấy user hoặc sai mật khẩu",null);
        return new ResponseData(Status.SUCCESS,"Đổi mật khẩu thành công",user);
    }

    @PostMapping("upload")
    public String uploadFile(@ModelAttribute(value = "image") MultipartFile image){
        return cloudinaryService.uploadFile(image);
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String userName,  @RequestParam(required = false) String email, @RequestParam(required = false) String sortBy){
        return new ResponseData(Status.SUCCESS,"Thành công",userService.search(page, size,userName,email,sortBy));
    }

    @GetMapping("get-by-username")
    public ResponseData search(@RequestParam String userName){
        User user = userService.getByUserName(userName);
        if (user == null)
            return new ResponseData(Status.SUCCESS,"Không tìm thấy user",user);
        return new ResponseData(Status.SUCCESS,"Thành công",user);
    }
}
