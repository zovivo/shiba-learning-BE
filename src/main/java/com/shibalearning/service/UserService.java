package com.shibalearning.service;

import com.shibalearning.entity.Role;
import com.shibalearning.entity.User;
import com.shibalearning.input.create.UserInput;
import com.shibalearning.input.create.UserLoginInput;
import com.shibalearning.input.update.UserUpdateInput;
import com.shibalearning.repository.RoleRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<User> findAll(){
        return  userRepository.findAll();
    }

    public User findById(long id){
        return  userRepository.findById(id);
    }

    public User login(UserLoginInput userLoginInput){
        User user = userRepository.findFirstByUserName(userLoginInput.getUserName());
        if (user == null)
            return null;
        else if (!user.getPassword().equals(userLoginInput.getPassword()))
            return null;
        return user;
    }

    public User create(UserInput userInput){
        if (userRepository.findFirstByUserName(userInput.getUserName()) != null){
            return null;
        }else {
            User user = new User(userInput);
            if(userInput.getType() == 1){
                Role teacher = roleRepository.findById(1);
                user.setRole(teacher);
            }else {
                Role student = roleRepository.findById(2);
                user.setRole(student);
            }
            if ( userInput.getAvatar() != null){
                String avatar = cloudinaryService.uploadFile(userInput.getAvatar());
                user.setAvatar(avatar);
            }
            user = userRepository.save(user);
            return user;
        }
    }

    public User updateProfile(UserUpdateInput userUpdateInput){
        User user = userRepository.findFirstByUserName(userUpdateInput.getUserName());
        if (user == null)
            return null;
        if (userUpdateInput.getNewAvatar() != null)
            user.setAvatar(cloudinaryService.uploadFile(userUpdateInput.getNewAvatar()));
        if (userUpdateInput.getNewEmail() != null)
            user.setEmail(userUpdateInput.getNewEmail());
        return userRepository.save(user);
    }

    public User changePassword(UserUpdateInput userUpdateInput){
        User user = userRepository.findFirstByUserName(userUpdateInput.getUserName());
        if (user == null)
            return null;
        if (userUpdateInput.getNewPassword() !=null && userUpdateInput.getPassword().equals(user.getPassword()))
            user.setPassword(userUpdateInput.getNewPassword());
        else
            return null;
        return userRepository.save(user);
    }

    public Page<User> search(int page, int size, String userName, String email, String sortBy){
        Pageable pageable = PageRequest.of(page, size);
        if (userName == null)
            userName = "";
        if (email == null)
            email = "";
        if (sortBy!=null && !sortBy.isEmpty())
            pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        Page<User> pageUser = userRepository.findAllByUserNameContainingAndAndEmailContaining(pageable,userName,email);
        return pageUser;
    }

    public User getByUserName(String userName){
        User user = userRepository.findFirstByUserName(userName);
        return  user;
    }

}
